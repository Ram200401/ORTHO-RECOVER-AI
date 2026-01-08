package com.example.ortho_shoulder_ai.util

import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import kotlin.math.roundToInt

class ShoulderPoseAnalyzer {

    enum class ExerciseType { FLEXION, ABDUCTION, UNKNOWN }
    enum class ExerciseState { START, UP, DOWN }

    private var repCount = 0
    private var currentState = ExerciseState.START
    private var maxAngleReached = 0
    private var minAngle = 30 // Threshold to consider the arm "down"
    private var maxThreshold = 150 // Threshold to consider a full rep

    data class ShoulderFeedback(
        val currentAngle: Int,
        val repCount: Int,
        val feedbackMessage: String,
        val isPostureCorrect: Boolean
    )

    fun analyze(result: PoseLandmarkerResult): ShoulderFeedback {
        if (result.landmarks().isEmpty()) {
            return ShoulderFeedback(0, repCount, "Position your full body", false)
        }

        val landmarks = result.landmarks()[0]
        
        // Use PoseDetectorHelper's angle calculation (Hip-Shoulder-Elbow)
        val leftAngle = PoseDetectorHelper.calculateAngle(result, 23, 11, 13).roundToInt()
        val rightAngle = PoseDetectorHelper.calculateAngle(result, 24, 12, 14).roundToInt()

        // Determine which side is more active/visible
        // For shoulder flexion, the angle increases from ~0 towards 180
        val currentAngle = Math.max(leftAngle, rightAngle)
        
        // Posture check: Is the torso vertical? (Shoulder to Hip angle relative to vertical)
        // We can check if the x-coordinates of shoulder and hip are broadly aligned
        val shoulder = if (leftAngle > rightAngle) landmarks[11] else landmarks[12]
        val hip = if (leftAngle > rightAngle) landmarks[23] else landmarks[24]
        val torsoLean = Math.abs(shoulder.x() - hip.x())
        val isPostureCorrect = torsoLean < 0.1f // Adjust threshold for lean detection

        // Rep counting logic
        var message = "Continue Flexion..."
        
        when (currentState) {
            ExerciseState.START -> {
                if (currentAngle > minAngle + 10) {
                    currentState = ExerciseState.UP
                }
                message = "Lift your arm slowly"
            }
            ExerciseState.UP -> {
                if (currentAngle > maxAngleReached) {
                    maxAngleReached = currentAngle
                }
                if (currentAngle < maxAngleReached - 15) {
                    // Arm started moving down
                    if (maxAngleReached > maxThreshold) {
                        // Successfully reached high enough
                        message = "Good range! Lower slowly"
                    } else if (maxAngleReached > 90) {
                        message = "Higher if possible!"
                    }
                    currentState = ExerciseState.DOWN
                }
            }
            ExerciseState.DOWN -> {
                if (currentAngle < minAngle) {
                    if (maxAngleReached > 90) { // Only count if they went above 90 degrees
                        repCount++
                    }
                    maxAngleReached = 0
                    currentState = ExerciseState.START
                    message = "Rep Complete! Go again"
                }
            }
        }

        if (!isPostureCorrect) {
            message = "Keep your back straight"
        }

        return ShoulderFeedback(
            currentAngle = currentAngle,
            repCount = repCount,
            feedbackMessage = message,
            isPostureCorrect = isPostureCorrect
        )
    }

    fun reset() {
        repCount = 0
        currentState = ExerciseState.START
        maxAngleReached = 0
    }
}
