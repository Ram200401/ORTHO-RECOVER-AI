package com.example.ortho_shoulder_ai.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue

@Composable
fun HelpInstructionsScreen(
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
            .padding(24.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Gray
                )
            }
        }

        Text(
            text = "Help & Instructions",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBlue,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ROM Tracking Section
        InstructionSection(
            icon = Icons.Outlined.CameraAlt,
            title = "ROM Tracking",
            instructions = listOf(
                "1. Place your phone on a stable surface or use a tripod.",
                "2. Stand 6-8 feet away so your full body is visible.",
                "3. Wear contrasting clothes (e.g., dark pants against a light wall).",
                "4. Follow the on-screen guides to align your body."
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Swelling Photos Section
        InstructionSection(
            icon = Icons.Outlined.FileUpload,
            title = "Swelling Photos",
            instructions = listOf(
                "1. Take photos in good lighting, preferably daylight.",
                "2. Take photos from the same angle each time for comparison.",
                "3. Ensure the affected area is clearly visible and in focus."
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Safety Section
        SafetySection()
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun InstructionSection(
    icon: ImageVector,
    title: String,
    instructions: List<String>
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF6C9BD1),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBlue
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Surface(
            color = Color(0xFFF9FBFF),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                instructions.forEachIndexed { index, instruction ->
                    Text(
                        text = instruction,
                        fontSize = 15.sp,
                        color = Color.Gray,
                        lineHeight = 22.sp
                    )
                    if (index < instructions.size - 1) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun SafetySection() {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.MonitorHeart,
                contentDescription = null,
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Safety",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBlue
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Surface(
            color = Color(0xFFFFF7F0),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "If you experience sharp pain, dizziness, or shortness of breath, stop exercising immediately and contact your healthcare provider.",
                    fontSize = 15.sp,
                    color = DarkBlue.copy(alpha = 0.8f),
                    lineHeight = 22.sp
                )
            }
        }
    }
}
