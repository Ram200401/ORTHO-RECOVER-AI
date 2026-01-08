package com.example.ortho_shoulder_ai.ui.screens.clinician

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicianPersonalInfoScreen(onBack: () -> Unit) {
    var fullName by remember { mutableStateOf("Dr. Nidhi") }
    var emailAddress by remember { mutableStateOf("orthocare.nidhi@example.com") }
    var contactNumber by remember { mutableStateOf("+91 98765 43210") }
    var licenseNumber by remember { mutableStateOf("MED-2024-12345") }
    var yearsOfExperience by remember { mutableStateOf("8") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personal Information", fontWeight = FontWeight.Bold, color = DarkBlue) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = DarkBlue)
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
            Spacer(modifier = Modifier.height(32.dp))
            
            // Full Name
            EditableField(
                label = "Full Name",
                value = fullName,
                onValueChange = { fullName = it }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Email Address
            EditableField(
                label = "Email Address",
                value = emailAddress,
                onValueChange = { emailAddress = it }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Contact Number
            EditableField(
                label = "Contact Number",
                value = contactNumber,
                onValueChange = { contactNumber = it }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // License Number
            EditableField(
                label = "License Number",
                value = licenseNumber,
                onValueChange = { licenseNumber = it }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Years of Experience
            EditableField(
                label = "Years of Experience",
                value = yearsOfExperience,
                onValueChange = { yearsOfExperience = it }
            )
            
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(32.dp))
            
            // Save Changes Button
            Button(
                onClick = { onBack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
            ) {
                Text("Save Changes", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun EditableField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(
            text = label,
            fontSize = 14.sp,
            color = DarkBlue,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFE2E8F0),
                focusedBorderColor = DarkBlue,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            singleLine = true
        )
    }
}
