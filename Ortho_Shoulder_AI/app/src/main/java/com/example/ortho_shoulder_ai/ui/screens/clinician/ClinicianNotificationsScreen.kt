package com.example.ortho_shoulder_ai.ui.screens.clinician

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicianNotificationsScreen(onBack: () -> Unit) {
    var newPatientAlerts by remember { mutableStateOf(true) }
    var romGoalAlerts by remember { mutableStateOf(true) }
    var weeklySummaries by remember { mutableStateOf(false) }
    var systemUpdates by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Control which notifications you receive about your patients and account.",
                fontSize = 15.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            NotificationToggleRow(
                title = "New Patient Alerts",
                description = "Get notified when a new patient is assigned to you.",
                checked = newPatientAlerts,
                onCheckedChange = { newPatientAlerts = it }
            )

            NotificationToggleRow(
                title = "ROM Goal Milestones",
                description = "Receive updates when patients achieve their ROM targets.",
                checked = romGoalAlerts,
                onCheckedChange = { romGoalAlerts = it }
            )

            NotificationToggleRow(
                title = "Weekly Summaries",
                description = "Receive a weekly overview of patient progress.",
                checked = weeklySummaries,
                onCheckedChange = { weeklySummaries = it }
            )

            NotificationToggleRow(
                title = "System Updates",
                description = "Stay informed about new app features and updates.",
                checked = systemUpdates,
                onCheckedChange = { systemUpdates = it }
            )
        }
    }
}

@Composable
fun NotificationToggleRow(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFF8FAFC),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = DarkBlue)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = description, fontSize = 13.sp, color = Color.Gray)
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = DarkBlue,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color(0xFFCBD5E1)
                )
            )
        }
    }
}
