package com.example.ortho_shoulder_ai.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.SystemClock
import androidx.camera.core.ImageProxy
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.core.Delegate
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarker
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import kotlin.math.atan2
import kotlin.math.PI

class PoseDetectorHelper(
    val context: Context,
    val minPoseDetectionConfidence: Float = 0.5f,
    val minPoseTrackingConfidence: Float = 0.5f,
    val minPosePresenceConfidence: Float = 0.5f,
    val delegate: Int = DELEGATE_CPU,
    var runningMode: RunningMode = RunningMode.LIVE_STREAM,
    val poseLandmarkerHelperListener: LandmarkerListener? = null
) {

    private var poseLandmarker: PoseLandmarker? = null

    init {
        setupPoseLandmarker()
    }

    fun setupPoseLandmarker() {
        val baseOptionBuilder = BaseOptions.builder()

        when (delegate) {
            DELEGATE_CPU -> baseOptionBuilder.setDelegate(Delegate.CPU)
            DELEGATE_GPU -> baseOptionBuilder.setDelegate(Delegate.GPU)
        }

        // Use a bundled model for simplicity in this session
        // Note: You might need to add the task file to assets
        baseOptionBuilder.setModelAssetPath("pose_landmarker_heavy.task")

        try {
            val optionsBuilder = PoseLandmarker.PoseLandmarkerOptions.builder()
                .setBaseOptions(baseOptionBuilder.build())
                .setMinPoseDetectionConfidence(minPoseDetectionConfidence)
                .setMinTrackingConfidence(minPoseTrackingConfidence)
                .setMinPosePresenceConfidence(minPosePresenceConfidence)
                .setRunningMode(runningMode)

            if (runningMode == RunningMode.LIVE_STREAM) {
                optionsBuilder.setResultListener(this::returnLivestreamResult)
                optionsBuilder.setErrorListener(this::returnLivestreamError)
            }

            poseLandmarker = PoseLandmarker.createFromOptions(context, optionsBuilder.build())
        } catch (e: IllegalStateException) {
            poseLandmarkerHelperListener?.onError("Pose landmarker failed to initialize. See error logs for details")
        } catch (e: Exception) {
            poseLandmarkerHelperListener?.onError(e.message ?: "Unknown error")
        }
    }

    fun detectLiveStream(imageProxy: ImageProxy, isFrontCamera: Boolean) {
        if (runningMode != RunningMode.LIVE_STREAM) {
            throw IllegalArgumentException("Attempting to call detectLiveStream while not in LIVE_STREAM mode.")
        }

        val frameTime = SystemClock.uptimeMillis()

        // Convert ImageProxy to Bitmap
        val bitmapBuffer = Bitmap.createBitmap(imageProxy.width, imageProxy.height, Bitmap.Config.ARGB_8888)
        imageProxy.use { bitmapBuffer.copyPixelsFromBuffer(imageProxy.planes[0].buffer) }
        imageProxy.close()

        val matrix = Matrix().apply {
            postRotate(imageProxy.imageInfo.rotationDegrees.toFloat())
            if (isFrontCamera) {
                postScale(-1f, 1f, imageProxy.width.toFloat() / 2, imageProxy.height.toFloat() / 2)
            }
        }

        val rotatedBitmap = Bitmap.createBitmap(
            bitmapBuffer, 0, 0, bitmapBuffer.width, bitmapBuffer.height, matrix, true
        )

        val mpImage = BitmapImageBuilder(rotatedBitmap).build()
        detectAsync(mpImage, frameTime)
    }

    private fun detectAsync(mpImage: MPImage, frameTime: Long) {
        poseLandmarker?.detectAsync(mpImage, frameTime)
    }

    private fun returnLivestreamResult(result: PoseLandmarkerResult, input: MPImage) {
        val finishTimeMs = SystemClock.uptimeMillis()
        val inferenceTime = finishTimeMs - result.timestampMs()

        poseLandmarkerHelperListener?.onResults(result, inferenceTime, input.height, input.width)
    }

    private fun returnLivestreamError(error: RuntimeException) {
        poseLandmarkerHelperListener?.onError(error.message ?: "Unknown error")
    }

    companion object {
        const val DELEGATE_CPU = 0
        const val DELEGATE_GPU = 1

        // Utility to calculate angle between three points
        fun calculateAngle(
            firstPoint: com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult,
            p1Idx: Int, p2Idx: Int, p3Idx: Int
        ): Double {
            if (firstPoint.landmarks().isEmpty()) return 0.0
            val landmarks = firstPoint.landmarks()[0]
            
            val p1 = landmarks[p1Idx]
            val p2 = landmarks[p2Idx]
            val p3 = landmarks[p3Idx]

            val angle = Math.toDegrees(
                atan2((p3.y() - p2.y()).toDouble(), (p3.x() - p2.x()).toDouble()) - 
                atan2((p1.y() - p2.y()).toDouble(), (p1.x() - p2.x()).toDouble())
            )
            
            var result = Math.abs(angle)
            if (result > 180) {
                result = 360 - result
            }
            return result
        }
    }

    interface LandmarkerListener {
        fun onError(error: String)
        fun onResults(result: PoseLandmarkerResult, inferenceTime: Long, imageHeight: Int, imageWidth: Int)
    }
}
