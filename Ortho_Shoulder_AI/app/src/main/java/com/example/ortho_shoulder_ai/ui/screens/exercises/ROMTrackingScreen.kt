package com.example.ortho_shoulder_ai.ui.screens.exercises

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue

@Composable
fun ROMTrackingScreen(
    exerciseName: String,
    onBack: () -> Unit,
    onReady: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Crosshair Layout
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                // Horizontal Line
                drawLine(
                    color = Color(0xFFFFCDD2), // Light red/pink
                    start = androidx.compose.ui.geometry.Offset(x = 0f, y = canvasHeight / 2),
                    end = androidx.compose.ui.geometry.Offset(x = canvasWidth, y = canvasHeight / 2),
                    strokeWidth = 1.dp.toPx()
                )

                // Vertical Line
                drawLine(
                    color = Color(0xFFFFCDD2), // Light red/pink
                    start = androidx.compose.ui.geometry.Offset(x = canvasWidth / 2, y = 0f),
                    end = androidx.compose.ui.geometry.Offset(x = canvasWidth / 2, y = canvasHeight),
                    strokeWidth = 1.dp.toPx()
                )
            }
        }

        // Setup Instructions
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 32.dp, vertical = 48.dp)
        ) {
            SetupItem(number = 1, text = "Place phone 6-8 feet away")
            Spacer(modifier = Modifier.height(24.dp))
            SetupItem(number = 2, text = "Ensure your full body is visible")
            Spacer(modifier = Modifier.height(24.dp))
            SetupItem(number = 3, text = "Wear contrasting clothes")
            
            Spacer(modifier = Modifier.height(48.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onReady),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "I'm Ready",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = DarkBlue,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun SetupItem(number: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            shape = CircleShape,
            color = Color(0xFF6C9BD1).copy(alpha = 0.6f),
            modifier = Modifier.size(36.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = number.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color(0xFFB0BEC5), // Light gray/blue text
            fontWeight = FontWeight.Normal
        )
    }
}
