package com.example.ortho_shoulder_ai.ui.screens.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue
import com.example.ortho_shoulder_ai.ui.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExercisesScreen(
    onNavigateToExerciseDetail: (String) -> Unit
) {
    val dailyExercises = listOf(
        DailyExercise("pendulum", "Pendulum Exercise (Shoulder)", "15 mins", "3 sets x 10"),
        DailyExercise("wall_pushups", "Wall Push-Ups", "5 mins", "2 sets x 20"),
        DailyExercise("cross_body", "Cross-Body Shoulder Stretch", "10 mins", "3 sets x 10"),
        DailyExercise("elbow_wrist", "Elbow, Wrist & Hand Movement", "12 mins", "3 sets x 10")
    )

    val completedCount = dailyExercises.count { it.isCompleted }
    val totalCount = dailyExercises.size
    val progress = completedCount.toFloat() / totalCount

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFBFDFF)) // Very light blue background
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // Header
        Text(
            text = "Daily Exercises",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBlue
        )
        Text(
            text = "Day 12 of Recovery Plan",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
        
        Spacer(modifier = Modifier.height(32.dp))

        // Progress Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Progress",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBlue
            )
            Text(
                text = "$completedCount of $totalCount completed",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = Color(0xFF8BA9D1),
            trackColor = Color(0xFFF0F4F8)
        )

        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            items(dailyExercises) { exercise ->
                DailyExerciseCard(
                    exercise = exercise,
                    onClick = { onNavigateToExerciseDetail(exercise.id) }
                )
            }
        }
    }
}

@Composable
fun DailyExerciseCard(
    exercise: DailyExercise,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = exercise.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkBlue
                    )
                    if (exercise.isCompleted) {
                        Spacer(modifier = Modifier.width(12.dp))
                        Surface(
                            color = Color(0xFFDFF6E8),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "DONE",
                                color = Color(0xFF4CAF50),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = exercise.duration,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(
                        modifier = Modifier
                            .size(3.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = exercise.reps,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            if (!exercise.isCompleted) {
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = DarkBlue
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Start",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

data class DailyExercise(
    val id: String,
    val title: String,
    val duration: String,
    val reps: String,
    val isCompleted: Boolean = false
)

