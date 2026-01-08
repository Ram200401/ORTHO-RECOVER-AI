package com.example.ortho_shoulder_ai.ui.screens.welcome

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue
import com.example.ortho_shoulder_ai.ui.theme.LightBlue
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun WelcomeScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToClinicianLogin: () -> Unit
) {
    var showContent by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        showContent = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Realistic Shoulder Animation
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp)
                .background(Color(0xFFF8F9FB), RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            RealisticShoulderExerciseAnimation()
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Staggered UI Entry
        AnimatedVisibility(
            visible = showContent,
            enter = fadeIn(animationSpec = tween(800, 200)) + expandVertically(animationSpec = tween(800, 200))
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Recovery Made Simple",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DarkBlue,
                    textAlign = TextAlign.Center,
                    letterSpacing = (-0.5).sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Your personal AI physiotherapist for a faster, safer recovery journey.",
                    fontSize = 17.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(56.dp))

        // Buttons with staggered entry
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn(animationSpec = tween(800, 600)) + slideInVertically(initialOffsetY = { 50 }, animationSpec = tween(800, 600))
            ) {
                Button(
                    onClick = onNavigateToLogin,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkBlue),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Text(
                        text = "Log In",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn(animationSpec = tween(800, 800)) + slideInVertically(initialOffsetY = { 50 }, animationSpec = tween(800, 800))
            ) {
                Button(
                    onClick = onNavigateToSignUp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Text(
                        text = "Create Account",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        AnimatedVisibility(
            visible = showContent,
            enter = fadeIn(animationSpec = tween(800, 1000))
        ) {
            TextButton(onClick = onNavigateToClinicianLogin) {
                Text(
                    text = "Are you a physiotherapist?",
                    color = DarkBlue,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun RealisticShoulderExerciseAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "recovery")
    
    // Animate shoulder flexion (arm lifting forward/up)
    // 0 is down, -120 is high forward lift
    val flexionAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -120f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flexion"
    )

    // Secondary animations for muscle effort/micro-movements
    val torsoShift by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2500), RepeatMode.Reverse),
        label = "shift"
    )

    Canvas(modifier = Modifier.size(300.dp)) {
        val centerX = size.width / 2.5f // Shift left for profile view
        val centerY = size.height / 2.2f
        val bodyColor = Color(0xFFF1F5F9)
        val muscleActiveColor = DarkBlue
        
        // 1. Draw Torso (Sideshot/Profile)
        drawRoundRect(
            color = bodyColor,
            topLeft = Offset(centerX - 40.dp.toPx(), centerY),
            size = androidx.compose.ui.geometry.Size(80.dp.toPx(), 180.dp.toPx()),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(30.dp.toPx()),
        )

        // 2. Draw Head
        drawCircle(
            color = bodyColor,
            radius = 35.dp.toPx(),
            center = Offset(centerX + torsoShift, centerY - 50.dp.toPx())
        )

        // 3. Draw Shoulder Joint (The Anchor)
        val shoulderX = centerX + 15.dp.toPx()
        val shoulderY = centerY + 25.dp.toPx()
        
        drawCircle(
            color = Color(0xFFE2E8F0),
            radius = 24.dp.toPx(),
            center = Offset(shoulderX, shoulderY)
        )

        // 4. Calculate Arm Joints (Anatomically principled)
        val upperArmLength = 80.dp.toPx()
        val lowerArmLength = 70.dp.toPx()
        
        // Upper Arm end point (Elbow)
        val radUpper = Math.toRadians(flexionAngle.toDouble() + 90.0)
        val elbowX = shoulderX + (upperArmLength * cos(radUpper)).toFloat()
        val elbowY = shoulderY + (upperArmLength * sin(radUpper)).toFloat()
        
        // Lower Arm end point (Wrist) - add slight natural curve/angle
        val lowerArmFlexion = flexionAngle * 0.2f // Slight natural elbow bend as you lift
        val radLower = Math.toRadians(flexionAngle.toDouble() + lowerArmFlexion.toDouble() + 90.0)
        val wristX = elbowX + (lowerArmLength * cos(radLower)).toFloat()
        val wristY = elbowY + (lowerArmLength * sin(radLower)).toFloat()

        // 5. Draw Arm Segments
        // Upper Arm
        drawLine(
            color = muscleActiveColor,
            start = Offset(shoulderX, shoulderY),
            end = Offset(elbowX, elbowY),
            strokeWidth = 32.dp.toPx(),
            cap = StrokeCap.Round
        )
        
        // Elbow Joint
        drawCircle(
            color = muscleActiveColor,
            radius = 16.dp.toPx(),
            center = Offset(elbowX, elbowY)
        )

        // Lower Arm
        drawLine(
            color = muscleActiveColor,
            start = Offset(elbowX, elbowY),
            end = Offset(wristX, wristY),
            strokeWidth = 26.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Hand/Wrist
        drawCircle(
            color = muscleActiveColor,
            radius = 18.dp.toPx(),
            center = Offset(wristX, wristY)
        )

        // 6. Visual Aids (Flexion Range Arc)
        drawArc(
            color = muscleActiveColor.copy(alpha = 0.08f),
            startAngle = 90f,
            sweepAngle = -120f,
            useCenter = false,
            topLeft = Offset(shoulderX - (upperArmLength + lowerArmLength), shoulderY - (upperArmLength + lowerArmLength)),
            size = androidx.compose.ui.geometry.Size((upperArmLength + lowerArmLength) * 2, (upperArmLength + lowerArmLength) * 2),
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )
        
        // Intensity markers (Innovative data touch)
        if (flexionAngle < -90f) {
            drawCircle(
                color = Color(0xFFEF4444).copy(alpha = 0.4f),
                radius = (10.dp.toPx() * ((-flexionAngle - 90)/30f)),
                center = Offset(shoulderX, shoulderY)
            )
        }
    }
}
