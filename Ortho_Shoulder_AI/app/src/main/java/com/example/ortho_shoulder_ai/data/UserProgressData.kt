package com.example.ortho_shoulder_ai.data

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

/**
 * Singleton object to store and manage user progress data across the app.
 * This allows sharing data between screens without complex state management.
 */
object UserProgressData {
    // Current ROM in degrees
    var currentRomDegrees = mutableIntStateOf(115)
        private set
    
    // Daily goal progress percentage
    var dailyGoalPercentage = mutableIntStateOf(45)
        private set
    
    // Exercise tracking
    private var completedExercisesToday = mutableIntStateOf(0)
    private var totalExercisesToday = mutableIntStateOf(4) // Default: 4 exercises per day
    
    /**
     * Update ROM value when a new exercise session is completed
     */
    fun updateROM(newRomDegrees: Int) {
        if (newRomDegrees > currentRomDegrees.intValue) {
            currentRomDegrees.intValue = newRomDegrees
        }
    }
    
    /**
     * Mark an exercise as completed and recalculate daily goal
     */
    fun markExerciseCompleted() {
        if (completedExercisesToday.intValue < totalExercisesToday.intValue) {
            completedExercisesToday.intValue++
            updateDailyGoal()
        }
    }
    
    /**
     * Calculate daily goal percentage based on completed exercises
     */
    private fun updateDailyGoal() {
        dailyGoalPercentage.intValue = 
            (completedExercisesToday.intValue * 100) / totalExercisesToday.intValue
    }
    
    /**
     * Reset daily progress (call this at the start of a new day)
     */
    fun resetDailyProgress() {
        completedExercisesToday.intValue = 0
        updateDailyGoal()
    }
    
    /**
     * Get current completion count
     */
    fun getCompletedCount(): Int = completedExercisesToday.intValue
    
    /**
     * Get total exercises for today
     */
    fun getTotalCount(): Int = totalExercisesToday.intValue
}
