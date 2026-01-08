package com.example.ortho_shoulder_ai.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue
import com.example.ortho_shoulder_ai.ui.theme.GrayText

@Composable
fun AboutAppScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "About App",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // App Icon Placeholder
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(DarkBlue),
                contentAlignment = Alignment.Center
            ) {
                // Using an icon as a placeholder for the app logo
                Icon(
                    imageVector = Icons.Default.AppRegistration,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Ortho Recover AI",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBlue
            )

            Text(
                text = "Version 1.0.2",
                fontSize = 14.sp,
                color = Color.Gray.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Disclaimer Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF9FBFF)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Disclaimer",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkBlue
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "This app is a tool to assist in your recovery but does not replace professional medical advice. Always consult with your surgeon or physiotherapist before starting any new exercise regimen.",
                        fontSize = 14.sp,
                        color = DarkBlue.copy(alpha = 0.8f),
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Footer
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = "© 2023 Ortho Recover AI. All rights reserved.",
                    fontSize = 12.sp,
                    color = Color.Gray.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    TextButton(onClick = { /* TODO */ }) {
                        Text(
                            text = "Privacy Policy",
                            fontSize = 12.sp,
                            color = Color(0xFF5A8BBD)
                        )
                    }
                    TextButton(onClick = { /* TODO */ }) {
                        Text(
                            text = "Terms of Service",
                            fontSize = 12.sp,
                            color = Color(0xFF5A8BBD)
                        )
                    }
                }
            }
        }
    }
}
