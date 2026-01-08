package com.example.ortho_shoulder_ai.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.util.Patterns
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue
import com.example.ortho_shoulder_ai.ui.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onBack: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmailError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        // ... (lines 38-77 unchanged)
        IconButton(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Gray)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Welcome Back",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )
                Text(
                    text = "Sign in to continue your recovery",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
            Surface(
                modifier = Modifier.size(56.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFF0F4F8)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp),
                    tint = DarkBlue
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(text = "Email Address", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkBlue)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { 
                email = it
                isEmailError = false
            },
            placeholder = { Text("name@example.com", color = Color.LightGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            isError = isEmailError,
            supportingText = {
                if (isEmailError) {
                    Text(text = "Please enter a valid email address", color = MaterialTheme.colorScheme.error)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFF0F4F8),
                focusedBorderColor = DarkBlue,
                unfocusedContainerColor = Color(0xFFF0F4F8),
                focusedContainerColor = Color(0xFFF0F4F8),
                errorContainerColor = Color(0xFFFFF0F0)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Password", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkBlue)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("••••••••", color = Color.LightGray) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFF0F4F8),
                focusedBorderColor = DarkBlue,
                unfocusedContainerColor = Color(0xFFF0F4F8),
                focusedContainerColor = Color(0xFFF0F4F8)
            )
        )

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            TextButton(onClick = onNavigateToForgotPassword) {
                Text(text = "Forgot Password?", color = LightBlue, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    onLoginSuccess()
                } else {
                    isEmailError = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
        ) {
            Text(text = "Sign In", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Don't have an account? ", color = Color.Gray, fontSize = 14.sp)
            TextButton(onClick = onNavigateToSignUp) {
                Text(text = "Sign Up", color = DarkBlue, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}
