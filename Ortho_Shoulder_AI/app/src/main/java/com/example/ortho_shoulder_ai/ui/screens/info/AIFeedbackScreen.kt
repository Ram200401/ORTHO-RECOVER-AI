package com.example.ortho_shoulder_ai.ui.screens.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled. VideocamOff
import androidx.compose.material.icons.outlined.CenterFocusStrong
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue
import com.example.ortho_shoulder_ai.ui.theme.LightBlue

@Composable
fun AIFeedbackScreen(
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        // Illustration Section
        Box(
            modifier = Modifier
                .size(280.dp)
                .background(Color(0xFFF8F9FA), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            // Placeholder for AI Tracking Illustration
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .background(LightBlue.copy(alpha = 0.2f), RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.CenterFocusStrong,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = LightBlue
                )
                
                // Representing the tracking point/dot from design
                Surface(
                    modifier = Modifier.size(16.dp).align(Alignment.Center),
                    shape = CircleShape,
                    color = Color(0xFF4CAF50)
                ) {}
            }
            
            // Scanner Frame effect
            Icon(
                imageVector = Icons.Default.CheckCircle, // Simplified scanner icon
                contentDescription = null,
                modifier = Modifier.size(48.dp).align(Alignment.BottomEnd).offset(x = 12.dp, y = 12.dp),
                tint = Color.White
            )
            Surface(
                modifier = Modifier.size(64.dp).align(Alignment.BottomEnd).offset(x = 12.dp, y = 12.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 4.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Outlined.CenterFocusStrong,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        tint = DarkBlue
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Real-time AI Feedback",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBlue,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Our advanced AI uses your camera to track your movement and ensure you're performing exercises correctly and safely.",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Info Cards
        InfoCard(
            icon = Icons.Default.VideocamOff,
            text = "No video is recorded or stored",
            iconColor = Color(0xFF4285F4)
        )

        Spacer(modifier = Modifier.height(16.dp))

        InfoCard(
            icon = Icons.Default.CheckCircle,
            text = "Instant posture correction",
            iconColor = Color(0xFF34A853)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
        ) {
            Text(text = "Continue", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun InfoCard(icon: ImageVector, text: String, iconColor: Color) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF8F9FA)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                fontSize = 15.sp,
                color = DarkBlue,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
