package com.example.ortho_shoulder_ai.ui.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue
import com.example.ortho_shoulder_ai.ui.theme.LightBlue
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    onNavigateToNext: () -> Unit
) {
    val scale = remember { Animatable(0.8f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Run animations simultaneously
        launch {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing)
            )
        }
        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1200, easing = LinearOutSlowInEasing)
            )
        }
        
        // Wait for animation to finish then navigate
        kotlinx.coroutines.delay(2000)
        onNavigateToNext()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Main Logo/Illustration
            Icon(
                imageVector = Icons.Default.MedicalServices,
                contentDescription = "Ortho Recover AI Logo",
                modifier = Modifier
                    .size(180.dp)
                    .scale(scale.value)
                    .alpha(alpha.value),
                tint = DarkBlue.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(64.dp))

            // Branding Text
            Text(
                text = "Ortho Recover AI",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBlue,
                modifier = Modifier.alpha(alpha.value)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle
            Text(
                text = "Advanced Joint Analysis",
                fontSize = 18.sp,
                color = LightBlue,
                modifier = Modifier.alpha(alpha.value)
            )
        }
    }
}
