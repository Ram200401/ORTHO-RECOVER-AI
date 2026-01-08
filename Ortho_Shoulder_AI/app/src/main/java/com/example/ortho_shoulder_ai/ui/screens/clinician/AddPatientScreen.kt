package com.example.ortho_shoulder_ai.ui.screens.clinician

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPatientScreen(
    onBack: () -> Unit,
    onPatientAdded: () -> Unit
) {
    var currentStep by remember { mutableStateOf(1) }
    
    // Step 1 State
    var fullName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    var mobile by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val patientId = remember { "P-${(1000..9999).random()}" }

    // Step 2 State
    var selectedSurgery by remember { mutableStateOf("Rotator Cuff Repair") }
    var surgeryDate by remember { mutableStateOf("") }
    var affectedShoulder by remember { mutableStateOf("Right") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Patient", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { if (currentStep == 1) onBack() else currentStep = 1 }) {
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
                .padding(horizontal = 24.dp)
        ) {
            // Step Indicator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StepIndicator(step = 1, currentStep = currentStep, modifier = Modifier.weight(1f))
                StepIndicator(step = 2, currentStep = currentStep, modifier = Modifier.weight(1f))
            }

            Box(modifier = Modifier.weight(1f)) {
                if (currentStep == 1) {
                    BasicDetailsStep(
                        fullName = fullName,
                        onFullNameChange = { fullName = it },
                        dob = dob,
                        onDobChange = { dob = it },
                        gender = gender,
                        onGenderChange = { gender = it },
                        mobile = mobile,
                        onMobileChange = { mobile = it },
                        email = email,
                        onEmailChange = { email = it },
                        patientId = patientId,
                        onNext = { currentStep = 2 }
                    )
                } else {
                    MedicalInfoStep(
                        selectedSurgery = selectedSurgery,
                        onSurgeryChange = { selectedSurgery = it },
                        surgeryDate = surgeryDate,
                        onSurgeryDateChange = { surgeryDate = it },
                        affectedShoulder = affectedShoulder,
                        onShoulderChange = { affectedShoulder = it },
                        onSubmit = onPatientAdded
                    )
                }
            }
        }
    }
}

@Composable
fun StepIndicator(step: Int, currentStep: Int, modifier: Modifier = Modifier) {
    val isActive = step <= currentStep
    Box(
        modifier = modifier
            .height(4.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(if (isActive) DarkBlue else Color(0xFFE2E8F0))
    )
}

@Composable
fun BasicDetailsStep(
    fullName: String,
    onFullNameChange: (String) -> Unit,
    dob: String,
    onDobChange: (String) -> Unit,
    gender: String,
    onGenderChange: (String) -> Unit,
    mobile: String,
    onMobileChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    patientId: String,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SectionTitle("1️⃣ Basic Patient Details", "These identify the patient")
        
        Spacer(modifier = Modifier.height(24.dp))

        PatientInputField(label = "Full Name *", value = fullName, onValueChange = onFullNameChange, placeholder = "Enter full name")
        PatientInputField(label = "Age / Date of Birth *", value = dob, onValueChange = onDobChange, placeholder = "DD/MM/YYYY or Age")
        
        Text("Gender *", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkBlue, modifier = Modifier.padding(bottom = 8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            GenderChip("Male", gender == "Male") { onGenderChange("Male") }
            GenderChip("Female", gender == "Female") { onGenderChange("Female") }
            GenderChip("Other", gender == "Other") { onGenderChange("Other") }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        PatientInputField(label = "Mobile Number *", value = mobile, onValueChange = onMobileChange, placeholder = "+91 00000 00000")
        PatientInputField(label = "Email ID (optional)", value = email, onValueChange = onEmailChange, placeholder = "example@mail.com")
        
        Spacer(modifier = Modifier.height(16.dp))
        Text("Patient ID", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkBlue)
        Text(text = patientId, fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))

        Spacer(modifier = Modifier.height(40.dp))
        
        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
        ) {
            Text("Next Step", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun MedicalInfoStep(
    selectedSurgery: String,
    onSurgeryChange: (String) -> Unit,
    surgeryDate: String,
    onSurgeryDateChange: (String) -> Unit,
    affectedShoulder: String,
    onShoulderChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    val surgeries = listOf("Rotator Cuff Repair", "Shoulder Arthroscopy", "Shoulder Impingement")
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SectionTitle("2️⃣ Medical & Surgical Information", "Core clinical data for tracking")
        
        Spacer(modifier = Modifier.height(24.dp))

        Text("Primary Condition / Surgery Type *", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkBlue, modifier = Modifier.padding(bottom = 12.dp))
        surgeries.forEach { surgery ->
            SurgeryOptionRow(surgery, selectedSurgery == surgery) { onSurgeryChange(surgery) }
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
        PatientInputField(label = "Surgery Date *", value = surgeryDate, onValueChange = onSurgeryDateChange, placeholder = "Select Date")
        
        Spacer(modifier = Modifier.height(16.dp))
        Text("Affected Shoulder *", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkBlue, modifier = Modifier.padding(bottom = 12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ShoulderChip("Left", affectedShoulder == "Left") { onShoulderChange("Left") }
            ShoulderChip("Right", affectedShoulder == "Right") { onShoulderChange("Right") }
            ShoulderChip("Both", affectedShoulder == "Both") { onShoulderChange("Both") }
        }

        Spacer(modifier = Modifier.height(56.dp))
        
        Button(
            onClick = onSubmit,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
        ) {
            Text("Add Patient", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun SectionTitle(title: String, subtitle: String) {
    Column {
        Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DarkBlue)
        Text(text = subtitle, fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun PatientInputField(label: String, value: String, onValueChange: (String) -> Unit, placeholder: String) {
    Column(modifier = Modifier.padding(bottom = 20.dp)) {
        Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkBlue, modifier = Modifier.padding(bottom = 8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = Color.LightGray) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = DarkBlue,
                unfocusedBorderColor = Color(0xFFE2E8F0)
            ),
            singleLine = true
        )
    }
}

@Composable
fun GenderChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        color = if (isSelected) DarkBlue else Color(0xFFF8FAFC),
        border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            color = if (isSelected) Color.White else Color.Gray,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}

@Composable
fun RowScope.ShoulderChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        color = if (isSelected) DarkBlue else Color(0xFFF8FAFC),
        border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(vertical = 12.dp)) {
            Text(
                text = label,
                color = if (isSelected) Color.White else Color.Gray,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun SurgeryOptionRow(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) DarkBlue.copy(alpha = 0.05f) else Color.White,
        border = androidx.compose.foundation.BorderStroke(
            1.dp, 
            if (isSelected) DarkBlue else Color(0xFFE2E8F0)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(selectedColor = DarkBlue)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) DarkBlue else Color.Gray
            )
        }
    }
}
