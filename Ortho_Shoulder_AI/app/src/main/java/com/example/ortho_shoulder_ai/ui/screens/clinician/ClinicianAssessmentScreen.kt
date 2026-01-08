package com.example.ortho_shoulder_ai.ui.screens.clinician

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue

@Composable
fun ClinicianAssessmentScreen(
    onBack: () -> Unit,
    onSendSuccess: () -> Unit
) {
    var selectedSeverity by remember { mutableStateOf("Mild") }
    var feedbackText by remember { mutableStateOf("The swelling looks consistent with post-exercise inflammation. Continue icing for 20 mins after sessions.") }
    var selectedRecommendation by remember { mutableStateOf("Reduce intensity") }

    val severities = listOf("None", "Mild", "Moderate", "Severe")
    val recommendations = listOf(
        "Continue current plan",
        "Reduce intensity",
        "Schedule video call",
        "Urgent check-up"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(bottom = 32.dp)
    ) {
        // Top Bar
        Box(modifier = Modifier.padding(16.dp)) {
            IconButton(onClick = onBack) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = "Back", tint = Color.Gray)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            // Header
            Text(
                text = "Assessment",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBlue
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Swelling Severity
            Text(
                text = "Swelling Severity",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBlue
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Severity Grid
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SeverityButton(
                        label = severities[0],
                        isSelected = selectedSeverity == severities[0],
                        onClick = { selectedSeverity = severities[0] },
                        modifier = Modifier.weight(1f)
                    )
                    SeverityButton(
                        label = severities[1],
                        isSelected = selectedSeverity == severities[1],
                        onClick = { selectedSeverity = severities[1] },
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SeverityButton(
                        label = severities[2],
                        isSelected = selectedSeverity == severities[2],
                        onClick = { selectedSeverity = severities[2] },
                        modifier = Modifier.weight(1f)
                    )
                    SeverityButton(
                        label = severities[3],
                        isSelected = selectedSeverity == severities[3],
                        onClick = { selectedSeverity = severities[3] },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Feedback for Patient
            Text(
                text = "Feedback for Patient",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBlue
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = feedbackText,
                onValueChange = { feedbackText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = DarkBlue.copy(alpha = 0.2f),
                    unfocusedContainerColor = Color(0xFFF8FAFC),
                    focusedContainerColor = Color(0xFFF8FAFC)
                ),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = DarkBlue.copy(alpha = 0.8f)
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Recommendations
            Text(
                text = "Recommendations",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBlue
            )
            Spacer(modifier = Modifier.height(16.dp))

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                recommendations.forEach { recommendation ->
                    RecommendationItem(
                        label = recommendation,
                        isSelected = selectedRecommendation == recommendation,
                        onClick = { selectedRecommendation = recommendation }
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Send Assessment Button
            Button(
                onClick = onSendSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
            ) {
                Text(
                    text = "Send Assessment",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SeverityButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(64.dp)
            .clickable { onClick() }
            .then(if (isSelected) Modifier.shadow(8.dp, RoundedCornerShape(16.dp)) else Modifier),
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) DarkBlue else Color.White,
        border = if (isSelected) null else BorderStroke(1.dp, Color(0xFFF1F5F9))
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = label,
                color = if (isSelected) Color.White else Color.Gray,
                fontSize = 16.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            )
        }
    }
}

@Composable
fun RecommendationItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF3B82F6),
                unselectedColor = Color.LightGray
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            fontSize = 15.sp,
            color = DarkBlue.copy(alpha = 0.8f)
        )
    }
}
