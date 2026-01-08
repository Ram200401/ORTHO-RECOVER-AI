package com.example.ortho_shoulder_ai.ui.screens.surgery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue
import com.example.ortho_shoulder_ai.ui.theme.LightBlue
import java.util.*
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurgeryDetailsScreen(
    onBack: () -> Unit,
    onSaveAndContinue: () -> Unit
) {
    var surgeryDate by remember { mutableStateOf("") }
    var selectedSide by remember { mutableStateOf("Right") }
    var painLevel by remember { mutableStateOf(3f) }
    
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = Date(millis)
                        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                        surgeryDate = formatter.format(date)
                    }
                    showDatePicker = false
                }) {
                    Text("OK", color = DarkBlue)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel", color = Color.Gray)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        IconButton(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Gray)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Surgery Details",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBlue
        )
        Text(
            text = "Help us personalize your recovery plan.",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Surgery Date Section
        Text(text = "Surgery Date", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkBlue)
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = surgeryDate,
            onValueChange = { surgeryDate = it },
            placeholder = { Text("mm/dd/yyyy", color = Color.LightGray) },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true },
            enabled = false, // Disable direct typing to force date picker
            trailingIcon = { 
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = "Select Date", tint = Color.Gray)
                }
            },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color(0xFFF0F4F8),
                disabledTextColor = DarkBlue,
                disabledPlaceholderColor = Color.LightGray,
                disabledContainerColor = Color(0xFFF0F4F8),
                focusedBorderColor = DarkBlue,
                unfocusedBorderColor = Color(0xFFF0F4F8),
                unfocusedContainerColor = Color(0xFFF0F4F8),
                focusedContainerColor = Color(0xFFF0F4F8)
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Operated Side Section
        Text(text = "Operated Side", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkBlue)
        Spacer(modifier = Modifier.height(12.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFF0F4F8)
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SideButton("Left", selectedSide == "Left", modifier = Modifier.weight(1f)) { selectedSide = "Left" }
                SideButton("Right", selectedSide == "Right", modifier = Modifier.weight(1f)) { selectedSide = "Right" }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Pain Level Section
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Pain Level", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkBlue)
                    val painColor = when (painLevel.toInt()) {
                        in 0..3 -> Color(0xFF2E7D32) // Green
                        in 4..7 -> Color(0xFFF57C00) // Orange
                        else -> Color(0xFFD32F2F) // Red (8-10)
                    }
                    Text(
                        text = "${painLevel.toInt()}/10",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = painColor
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Slider(
                    value = painLevel,
                    onValueChange = { painLevel = it },
                    valueRange = 0f..10f,
                    steps = 9,
                    colors = SliderDefaults.colors(
                        thumbColor = DarkBlue,
                        activeTrackColor = Color(0xFFD1D9E6),
                        inactiveTrackColor = Color(0xFFF0F4F8)
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "No Pain", fontSize = 12.sp, color = Color.LightGray)
                    Text(text = "Moderate", fontSize = 12.sp, color = Color.LightGray)
                    Text(text = "Severe", fontSize = 12.sp, color = Color.LightGray)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onSaveAndContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
        ) {
            Text(text = "Save and Continue", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun SideButton(label: String, isSelected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier
            .fillMaxHeight()
            .padding(2.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) Color.White else Color.Transparent,
        shadowElevation = if (isSelected) 2.dp else 0.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isSelected) Color(0xFF4285F4) else Color.Gray // Blue for selected
            )
        }
    }
}
