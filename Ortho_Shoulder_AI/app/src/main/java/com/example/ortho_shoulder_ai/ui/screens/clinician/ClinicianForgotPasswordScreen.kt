package com.example.ortho_shoulder_ai.ui.screens.clinician

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun ClinicianForgotPasswordScreen(
    onBack: () -> Unit,
    onResetSuccess: () -> Unit
) {
    var emailOrPhone by remember { mutableStateOf("") }

    // White background as per design
    val backgroundColor = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .statusBarsPadding()
            .padding(24.dp)
    ) {
        // Back Button
        IconButton(
            onClick = onBack,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Title
        Text(
            text = "Forgot Password",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBlue
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
            text = "Enter the email address or phone number associated with your account. We'll send you a link to reset your password.",
            fontSize = 16.sp,
            color = Color.Gray,
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Input Label
        Text(
            text = "Email Address or Phone Number",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkBlue,
            modifier = Modifier.padding(start = 4.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Input Field
        OutlinedTextField(
            value = emailOrPhone,
            onValueChange = { emailOrPhone = it },
            placeholder = { 
                Text(
                    "Enter your email or phone", 
                    color = Color.LightGray,
                    fontSize = 16.sp
                ) 
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFF0F4F8),
                focusedBorderColor = DarkBlue,
                unfocusedContainerColor = Color(0xFFF8FAFC),
                focusedContainerColor = Color(0xFFF8FAFC)
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.weight(1f))

        // Reset Password Button
        Button(
            onClick = onResetSuccess,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue
            )
        ) {
            Text(
                "Reset Password",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
