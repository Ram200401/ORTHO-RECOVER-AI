package com.example.ortho_shoulder_ai.util

import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult

interface PoseAnalyzer {
    fun analyze(result: PoseLandmarkerResult): ShoulderPoseAnalyzer.ShoulderFeedback
    fun reset()
}

class ShoulderFlexionAnalyzer : PoseAnalyzer {
    private val delegate = ShoulderPoseAnalyzer()
    
    override fun analyze(result: PoseLandmarkerResult): ShoulderPoseAnalyzer.ShoulderFeedback {
        return delegate.analyze(result)
    }

    override fun reset() {
        delegate.reset()
    }
}

class ShoulderAbductionAnalyzer : PoseAnalyzer {
    // Similar to flexion, but focuses on lateral lifting
    private var repCount = 0
    private var maxAngleReached = 0
    
    override fun analyze(result: PoseLandmarkerResult): ShoulderPoseAnalyzer.ShoulderFeedback {
        if (result.landmarks().isEmpty()) {
            return ShoulderPoseAnalyzer.ShoulderFeedback(0, repCount, "Position your full body", false)
        }

        val leftAngle = PoseDetectorHelper.calculateAngle(result, 23, 11, 13) // This might need adjustment for abduction plane
        val rightAngle = PoseDetectorHelper.calculateAngle(result, 24, 12, 14)
        val currentAngle = Math.max(leftAngle, rightAngle).toInt()

        // Placeholder logic for abduction rep counting
        return ShoulderPoseAnalyzer.ShoulderFeedback(
            currentAngle = currentAngle,
            repCount = repCount,
            feedbackMessage = "Performing Abduction...",
            isPostureCorrect = true
        )
    }

    override fun reset() {
        repCount = 0
        maxAngleReached = 0
    }
}

enum class SessionState {
    IDLE,
    CALIBRATING,
    ACTIVE,
    PAUSED
}

class ExerciseSessionManager(
    val exerciseName: String,
    val onCalibrationComplete: () -> Unit
) {
    var state = SessionState.IDLE
    var calibrationProgress = 0f
    private var analyzer: PoseAnalyzer = when {
        exerciseName.contains("Flexion", ignoreCase = true) -> ShoulderFlexionAnalyzer()
        exerciseName.contains("Abduction", ignoreCase = true) -> ShoulderAbductionAnalyzer()
        else -> ShoulderFlexionAnalyzer() // Default
    }

    fun processFrame(result: PoseLandmarkerResult): ShoulderPoseAnalyzer.ShoulderFeedback {
        return when (state) {
            SessionState.CALIBRATING -> {
                updateCalibration(result)
                ShoulderPoseAnalyzer.ShoulderFeedback(0, 0, "Calibrating AI Scan...", true)
            }
            SessionState.ACTIVE -> analyzer.analyze(result)
            else -> ShoulderPoseAnalyzer.ShoulderFeedback(0, 0, "Wait...", true)
        }
    }

    private fun updateCalibration(result: PoseLandmarkerResult) {
        if (result.landmarks().isNotEmpty()) {
            val landmarks = result.landmarks()[0]
            val allVisible = listOf(11, 12, 23, 24, 13, 14, 15, 16).all { 
                landmarks[it].presence().orElse(0f) > 0.5f 
            }
            
            if (allVisible) {
                calibrationProgress += 0.05f
                if (calibrationProgress >= 1f) {
                    state = SessionState.ACTIVE
                    onCalibrationComplete()
                }
            } else {
                calibrationProgress = (calibrationProgress - 0.02f).coerceAtLeast(0f)
            }
        }
    }
    
    fun start() {
        state = SessionState.CALIBRATING
        calibrationProgress = 0f
    }
    
    fun togglePause() {
        state = if (state == SessionState.PAUSED) SessionState.ACTIVE else SessionState.PAUSED
    }
}
