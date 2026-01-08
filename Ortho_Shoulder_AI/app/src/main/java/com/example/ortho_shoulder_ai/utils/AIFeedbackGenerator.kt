package com.example.ortho_shoulder_ai.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class FeedbackItem(
    val text: String,
    val icon: ImageVector,
    val iconColor: Color
)

object AIFeedbackGenerator {
    
    /**
     * Generate dynamic AI feedback based on exercise performance
     */
    fun generateFeedback(
        exerciseName: String,
        accuracy: Int,
        rom: Int
    ): List<FeedbackItem> {
        val feedback = mutableListOf<FeedbackItem>()
        
        // Accuracy-based feedback
        when {
            accuracy >= 90 -> {
                feedback.add(
                    FeedbackItem(
                        text = "Excellent form! Your technique is outstanding.",
                        icon = Icons.Default.Check,
                        iconColor = Color(0xFF4CAF50)
                    )
                )
            }
            accuracy >= 75 -> {
                feedback.add(
                    FeedbackItem(
                        text = "Good shoulder stability throughout.",
                        icon = Icons.Default.Check,
                        iconColor = Color(0xFF4CAF50)
                    )
                )
            }
            accuracy >= 60 -> {
                feedback.add(
                    FeedbackItem(
                        text = "Decent form. Focus on maintaining proper posture.",
                        icon = Icons.Default.Info,
                        iconColor = Color(0xFF2196F3)
                    )
                )
            }
            else -> {
                feedback.add(
                    FeedbackItem(
                        text = "Try to maintain better form throughout the exercise.",
                        icon = Icons.Default.Warning,
                        iconColor = Color(0xFFFFB300)
                    )
                )
            }
        }
        
        // ROM-based feedback
        when {
            rom >= 150 -> {
                feedback.add(
                    FeedbackItem(
                        text = "Outstanding range of motion achieved!",
                        icon = Icons.Default.Check,
                        iconColor = Color(0xFF4CAF50)
                    )
                )
            }
            rom >= 120 -> {
                feedback.add(
                    FeedbackItem(
                        text = "Great progress on your range of motion.",
                        icon = Icons.Default.Check,
                        iconColor = Color(0xFF4CAF50)
                    )
                )
            }
            rom >= 90 -> {
                feedback.add(
                    FeedbackItem(
                        text = "Good ROM. Keep working to increase your range.",
                        icon = Icons.Default.Info,
                        iconColor = Color(0xFF2196F3)
                    )
                )
            }
            else -> {
                feedback.add(
                    FeedbackItem(
                        text = "Focus on gradually increasing your range of motion.",
                        icon = Icons.Default.Warning,
                        iconColor = Color(0xFFFFB300)
                    )
                )
            }
        }
        
        // Exercise-specific feedback
        if (accuracy >= 70) {
            feedback.add(
                FeedbackItem(
                    text = "Excellent pace on the descent.",
                    icon = Icons.Default.Check,
                    iconColor = Color(0xFF4CAF50)
                )
            )
        } else {
            feedback.add(
                FeedbackItem(
                    text = "Try to keep your elbow slightly straighter next time.",
                    icon = Icons.Default.Warning,
                    iconColor = Color(0xFFFFB300)
                )
            )
        }
        
        return feedback
    }
}
