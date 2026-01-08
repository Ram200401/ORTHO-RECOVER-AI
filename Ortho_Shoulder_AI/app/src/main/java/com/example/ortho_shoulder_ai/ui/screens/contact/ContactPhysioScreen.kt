package com.example.ortho_shoulder_ai.ui.screens.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactPhysioScreen(
    onBack: () -> Unit
) {
    var message by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var isCallbackMode by remember { mutableStateOf(false) }
    var selectedTimeSlot by remember { mutableStateOf("Morning (9 AM - 12 PM)") }
    var callbackReason by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top App Bar
            TopAppBar(
                title = {
                    Text(
                        "Contact Physiotherapist",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF1A1A2E)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF1A1A2E)
                )
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Mode Selector
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Send Message Button
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = if (!isCallbackMode) Color(0xFF4A90E2) else Color.Transparent,
                        onClick = { isCallbackMode = false }
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "Send Message",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (!isCallbackMode) Color.White else Color(0xFF666666)
                            )
                        }
                    }
                    
                    // Request Callback Button
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = if (isCallbackMode) Color(0xFF4A90E2) else Color.Transparent,
                        onClick = { isCallbackMode = true }
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "Request Callback",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isCallbackMode) Color.White else Color(0xFF666666)
                            )
                        }
                    }
                }

                // Info Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF5F5F5)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (isCallbackMode) "Request a Callback" else "Send a Message",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A1A2E)
                        )
                        Text(
                            text = if (isCallbackMode) 
                                "Select your preferred time slot and our physiotherapist will call you back."
                            else 
                                "Your physiotherapist will receive your message and respond as soon as possible.",
                            fontSize = 14.sp,
                            color = Color(0xFF666666),
                            lineHeight = 20.sp
                        )
                    }
                }

                // Content Area - Message or Callback Form
                if (!isCallbackMode) {
                    // Message Input
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF5F5F5)
                        )
                    ) {
                        OutlinedTextField(
                            value = message,
                            onValueChange = { message = it },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            placeholder = {
                                Text(
                                    "Type your message here...",
                                    color = Color(0xFF999999)
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color(0xFF1A1A2E),
                                unfocusedTextColor = Color(0xFF1A1A2E),
                                focusedBorderColor = Color(0xFF4A90E2),
                                unfocusedBorderColor = Color.Transparent,
                                cursorColor = Color(0xFF4A90E2)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                } else {
                    // Callback Form
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Time Slot Selection
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF5F5F5)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = "Preferred Time Slot",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1A1A2E)
                                )
                                
                                val timeSlots = listOf(
                                    "Morning (9 AM - 12 PM)",
                                    "Afternoon (12 PM - 4 PM)",
                                    "Evening (4 PM - 7 PM)"
                                )
                                
                                timeSlots.forEach { slot ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable { selectedTimeSlot = slot }
                                            .background(
                                                if (selectedTimeSlot == slot) 
                                                    Color(0xFF4A90E2).copy(alpha = 0.1f)
                                                else 
                                                    Color.Transparent
                                            )
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = selectedTimeSlot == slot,
                                            onClick = { selectedTimeSlot = slot },
                                            colors = RadioButtonDefaults.colors(
                                                selectedColor = Color(0xFF4A90E2)
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = slot,
                                            fontSize = 14.sp,
                                            color = Color(0xFF1A1A2E)
                                        )
                                    }
                                }
                            }
                        }
                        
                        // Reason for Callback
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF5F5F5)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Reason for Callback (Optional)",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1A1A2E)
                                )
                                OutlinedTextField(
                                    value = callbackReason,
                                    onValueChange = { callbackReason = it },
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 8.dp),
                                    placeholder = {
                                        Text(
                                            "Brief description of what you'd like to discuss...",
                                            color = Color(0xFF999999)
                                        )
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = Color(0xFF1A1A2E),
                                        unfocusedTextColor = Color(0xFF1A1A2E),
                                        focusedBorderColor = Color(0xFF4A90E2),
                                        unfocusedBorderColor = Color.Transparent,
                                        cursorColor = Color(0xFF4A90E2)
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                )
                            }
                        }
                    }
                }

                // Submit Button
                Button(
                    onClick = {
                        if (!isCallbackMode && message.isNotBlank()) {
                            // TODO: Send message to backend
                            showSuccessDialog = true
                        } else if (isCallbackMode) {
                            // TODO: Send callback request to backend
                            showSuccessDialog = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = if (isCallbackMode) true else message.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4A90E2),
                        disabledContainerColor = Color(0xFF4A90E2).copy(alpha = 0.3f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = if (isCallbackMode) Icons.Default.Phone else Icons.Default.Send,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        if (isCallbackMode) "Request Callback" else "Send Message",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    // Success Dialog
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(
                    if (isCallbackMode) "Callback Requested" else "Message Sent",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    if (isCallbackMode) 
                        "Your callback request has been submitted. The physiotherapist will call you during the selected time slot."
                    else 
                        "Your message has been sent to your physiotherapist successfully."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        message = ""
                        callbackReason = ""
                        isCallbackMode = false
                        onBack()
                    }
                ) {
                    Text("OK")
                }
            },
            containerColor = Color.White,
            titleContentColor = Color(0xFF1A1A2E),
            textContentColor = Color(0xFF666666)
        )
    }
}
