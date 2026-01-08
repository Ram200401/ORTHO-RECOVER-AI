package com.example.ortho_shoulder_ai.ui.screens.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue

import com.example.ortho_shoulder_ai.data.model.ExerciseRepo

@Composable
fun ExerciseDetailScreen(
    exerciseId: String,
    onBack: () -> Unit,
    onStartTracking: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    // Fetch exercise data from repository
    val exercise = remember(exerciseId) {
        ExerciseRepo.getExerciseById(exerciseId)
    }

    // Fallback data if exercise not found
    val exerciseName = exercise?.name ?: "Exercise Detail"
    val instructions = exercise?.instructions ?: listOf("Setup the camera.", "Perform the exercise.")
    val safetyTip = exercise?.safetyTip ?: "Listen to your body."
    
    val sets = "3 Sets"
    val reps = "10 Reps"
    val duration = "3 Mins"

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Hero Image Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            ) {
                // Background Placeholder Color/Pattern
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFE0E0E0)) // Light gray placeholder
                ) {
                    // In a real app, use AsyncImage here
                    Text(
                        text = "Exercise Illustration",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Gray
                    )
                }

                // Back Button
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(16.dp)
                        .size(40.dp)
                        .background(Color.White.copy(alpha = 0.8f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = DarkBlue
                    )
                }

                // Watch Video Button Overlay
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(24.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White.copy(alpha = 0.9f),
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Videocam,
                            contentDescription = null,
                            tint = DarkBlue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Watch Video",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkBlue
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(24.dp)) {
                // Title and Stats
                Text(
                    text = exerciseName,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(text = sets, fontSize = 15.sp, color = Color.Gray)
                    Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(Color(0xFFD0D0D0)))
                    Text(text = reps, fontSize = 15.sp, color = Color.Gray)
                    Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(Color(0xFFD0D0D0)))
                    Text(text = duration, fontSize = 15.sp, color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Instructions Header
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color(0xFF6C9BD1),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Instructions",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkBlue
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Vertical Timeline Instructions
                instructions.forEachIndexed { index, instruction ->
                    InstructionStep(
                        number = index + 1,
                        text = instruction,
                        isLast = index == instructions.size - 1
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Safety Tip Card
                Surface(
                    color = Color(0xFFFFF9F0),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFFFA726).copy(alpha = 0.1f),
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = Color(0xFFE65100),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "Safety Tip",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE65100)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = safetyTip,
                                fontSize = 13.sp,
                                color = Color(0xFFE65100).copy(alpha = 0.8f),
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(100.dp)) // Padding for bottom button
            }
        }

        // Action Button Sticky at bottom
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(16.dp),
            color = DarkBlue,
            shadowElevation = 8.dp
        ) {
            Button(
                onClick = { onStartTracking(exerciseName) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.PhotoCamera,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Start ROM Tracking",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun InstructionStep(
    number: Int,
    text: String,
    isLast: Boolean
) {
    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(32.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = Color(0xFFF0F4F8),
                modifier = Modifier.size(24.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = number.toString(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6C9BD1)
                    )
                }
            }
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .weight(1f)
                        .background(Color(0xFFF0F4F8))
                )
            }
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Text(
            text = text,
            fontSize = 15.sp,
            color = Color.Gray,
            lineHeight = 22.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}
