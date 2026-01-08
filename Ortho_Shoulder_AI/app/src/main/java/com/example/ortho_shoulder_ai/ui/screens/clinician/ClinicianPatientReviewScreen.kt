package com.example.ortho_shoulder_ai.ui.screens.clinician

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue

@Composable
fun ClinicianPatientReviewScreen(
    onBack: () -> Unit,
    onNavigateToSwellingReview: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
    ) {
        // Header
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
            IconButton(onClick = onBack) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = "Back", tint = DarkBlue)
            }
        }

        // Patient Intro
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(DarkBlue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "S",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    text = "Sarah Johnson",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )
                Text(
                    text = "Rotator Cuff Repair • 3 Weeks Post-op",
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedActionButton(icon = Icons.Default.Call, label = "Call", modifier = Modifier.weight(1f))
            OutlinedActionButton(icon = Icons.Default.Email, label = "Email", modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Data Cards
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DetailDataCard(
                icon = Icons.Default.ShowChart,
                title = "ROM Data",
                subtitle = "Last: 115° Flexion",
                backgroundColor = Color(0xFFF0F7FF),
                iconColor = Color(0xFF3B82F6),
                modifier = Modifier.weight(1f)
            )
            DetailDataCard(
                icon = Icons.Default.Image,
                title = "Swelling",
                subtitle = "Review Pending",
                backgroundColor = Color(0xFFFFFAF5),
                iconColor = Color(0xFFF97316),
                modifier = Modifier
                    .weight(1f)
                    .clickable { onNavigateToSwellingReview() }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Current Plan Section
        SectionHeader(title = "Current Plan", action = "Edit")
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFFF8FAFC)
        ) {
            Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                PlanItem("Pendulum Exercise (3×10)")
                PlanItem("Wall Climb (3×10)")
                PlanItem("Wall Slides (2×20)")
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Clinical Notes Section
        SectionHeader(title = "Clinical Notes")
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFFF8FAFC)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Patient reporting mild pain (3/10) during extension. Swelling has decreased significantly since last week. Incision site looks clean.",
                    fontSize = 15.sp,
                    color = DarkBlue.copy(alpha = 0.8f),
                    lineHeight = 24.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Last updated: Oct 24, 2023",
                    fontSize = 13.sp,
                    color = Color.LightGray
                )
            }
        }
    }
}

@Composable
fun OutlinedActionButton(icon: ImageVector, label: String, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = { },
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(28.dp),
        border = BorderStroke(1.dp, DarkBlue.copy(alpha = 0.5f)),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = DarkBlue)
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 15.sp)
    }
}

@Composable
fun DetailDataCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    backgroundColor: Color,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.height(120.dp),
        shape = RoundedCornerShape(24.dp),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(28.dp))
            Column {
                Text(text = title, fontWeight = FontWeight.Bold, color = DarkBlue, fontSize = 15.sp)
                Text(text = subtitle, color = Color.Gray, fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, action: String? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkBlue)
        if (action != null) {
            Text(
                text = action,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF3B82F6),
                modifier = Modifier.clickable { }
            )
        }
    }
}

@Composable
fun PlanItem(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(Color(0xFF3B82F6).copy(alpha = 0.4f))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, fontSize = 16.sp, color = DarkBlue.copy(alpha = 0.7f))
    }
}

