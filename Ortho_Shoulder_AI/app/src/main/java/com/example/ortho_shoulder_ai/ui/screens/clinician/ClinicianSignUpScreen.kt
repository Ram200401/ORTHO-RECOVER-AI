package com.example.ortho_shoulder_ai.ui.screens.clinician

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicianSignUpScreen(
    onBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onSignUpSuccess: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var licenseNumber by remember { mutableStateOf("") }
    var yearsOfExperience by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false) }
    var selectedFileName by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    val context = LocalContext.current
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            // Get file name from Uri (simplified for demonstration)
            selectedFileName = it.path?.substringAfterLast("/") ?: "Document Uploaded"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        
        // Back Button
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .size(24.dp)
                .clickable { onBack() },
            tint = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Text(
            text = "Create Account",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBlue
        )
        Text(
            text = "Where physiotherapy meets intelligent recovery",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Personal Details
        SignUpTextField(label = "Full Name", value = fullName, onValueChange = { fullName = it }, placeholder = "John Doe")
        Spacer(modifier = Modifier.height(24.dp))
        SignUpTextField(label = "Email Address", value = email, onValueChange = { email = it }, placeholder = "name@example.com")
        Spacer(modifier = Modifier.height(24.dp))
        SignUpTextField(label = "Phone No.", value = phone, onValueChange = { phone = it }, placeholder = "")
        Spacer(modifier = Modifier.height(24.dp))
        SignUpTextField(
            label = "Password", 
            value = password, 
            onValueChange = { password = it }, 
            placeholder = "Create a password",
            isPassword = true
        )
        Text(
            text = "Must be at least 8 characters",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp, start = 4.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        SignUpTextField(
            label = "Confirm Password", 
            value = confirmPassword, 
            onValueChange = { confirmPassword = it }, 
            placeholder = "",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Professional Details Section
        Text(
            text = "Professional Details",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBlue
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        SignUpTextField(label = "Registration/License Number", value = licenseNumber, onValueChange = { licenseNumber = it }, placeholder = "")
        Spacer(modifier = Modifier.height(24.dp))
        SignUpTextField(label = "Years of Experience", value = yearsOfExperience, onValueChange = { yearsOfExperience = it }, placeholder = "")

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Upload License",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkBlue,
            modifier = Modifier.padding(start = 4.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        
        // Upload Button
        OutlinedButton(
            onClick = { filePickerLauncher.launch("*/*") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, DarkBlue),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = DarkBlue)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.FileUpload, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Upload Document", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        selectedFileName?.let { fileName ->
            Text(
                text = "Selected: $fileName",
                fontSize = 12.sp,
                color = DarkBlue,
                modifier = Modifier.padding(top = 8.dp, start = 4.dp),
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Terms and Conditions
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = termsAccepted,
                onCheckedChange = { termsAccepted = it },
                colors = CheckboxDefaults.colors(checkedColor = DarkBlue)
            )
            Text(text = "I agree to the ", fontSize = 14.sp, color = Color.Gray)
            Text(text = "Terms of Service", fontSize = 14.sp, color = DarkBlue, fontWeight = FontWeight.SemiBold)
            Text(text = " and ", fontSize = 14.sp, color = Color.Gray)
            Text(text = "Privacy Policy", fontSize = 14.sp, color = DarkBlue, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Create Account Button
        Button(
            onClick = {
                when {
                    fullName.isBlank() -> errorMessage = "Please enter your full name"
                    email.isBlank() -> errorMessage = "Please enter your email"
                    phone.isBlank() -> errorMessage = "Please enter your phone number"
                    password.length < 8 -> errorMessage = "Password must be at least 8 characters"
                    password != confirmPassword -> errorMessage = "Passwords do not match"
                    licenseNumber.isBlank() -> errorMessage = "Please enter your license number"
                    yearsOfExperience.isBlank() -> errorMessage = "Please enter your experience"
                    selectedFileName == null -> errorMessage = "Please upload your license document"
                    !termsAccepted -> errorMessage = "Please accept the Terms of Service"
                    else -> {
                        errorMessage = null
                        onSignUpSuccess()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
        ) {
            Text(text = "Create Account", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Log In Footer
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Already have an account? ", color = Color.Gray, fontSize = 14.sp)
            TextButton(
                onClick = onNavigateToLogin,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(text = "Log In", color = DarkBlue, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun SignUpTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkBlue,
            modifier = Modifier.padding(start = 4.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.LightGray) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFF0F4F8),
                focusedBorderColor = DarkBlue,
                unfocusedContainerColor = Color(0xFFF8FAFC),
                focusedContainerColor = Color(0xFFF8FAFC)
            )
        )
    }
}
