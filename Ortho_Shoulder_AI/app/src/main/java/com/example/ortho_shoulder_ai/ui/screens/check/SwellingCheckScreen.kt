package com.example.ortho_shoulder_ai.ui.screens.check

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue
import com.example.ortho_shoulder_ai.ui.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwellingCheckScreen(
    onBack: () -> Unit,
    onSubmit: () -> Unit
) {
    var painLevel by remember { mutableStateOf(3f) }
    var patientNote by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val scrollState = rememberScrollState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
            .padding(24.dp)
    ) {
        IconButton(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Gray)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Swelling Check",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBlue
        )
        Text(
            text = "Upload a photo for your physiotherapist to review.",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Upload Section with Dashed Border
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .padding(vertical = 8.dp)
                .clickable { imagePickerLauncher.launch("image/*") }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRoundRect(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    style = Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    ),
                    cornerRadius = CornerRadius(24.dp.toPx())
                )
            }
            
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (selectedImageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(selectedImageUri),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .size(160.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Change Image",
                        fontSize = 14.sp,
                        color = DarkBlue,
                        fontWeight = FontWeight.SemiBold
                    )
                } else {
                    Surface(
                        modifier = Modifier.size(80.dp),
                        shape = CircleShape,
                        color = Color(0xFFF0F4F8)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = null,
                            tint = DarkBlue.copy(alpha = 0.7f),
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Take a Photo", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkBlue)
                    Text(text = "or upload from gallery", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    OutlinedButton(
                        onClick = { imagePickerLauncher.launch("image/*") },
                        shape = RoundedCornerShape(12.dp),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = androidx.compose.ui.graphics.SolidColor(DarkBlue)
                        ),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FileUpload, 
                            contentDescription = null, 
                            tint = DarkBlue,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Select Image", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkBlue)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Body Part Section
        Text(text = "Body Part", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkBlue)
        Spacer(modifier = Modifier.height(12.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            color = Color.White
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Left Shoulder", fontSize = 18.sp, color = DarkBlue)
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null, tint = DarkBlue)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Pain Level Section
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Pain Level", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkBlue)
                    Text(
                        text = "${painLevel.toInt()}/10",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

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
                    Text(text = "No Pain", fontSize = 12.sp, color = Color.Gray.copy(alpha = 0.6f))
                    Text(text = "Moderate", fontSize = 12.sp, color = Color.Gray.copy(alpha = 0.6f))
                    Text(text = "Severe", fontSize = 12.sp, color = Color.Gray.copy(alpha = 0.6f))
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Patient Note
        Text(text = "Note about swelling", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkBlue)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = patientNote,
            onValueChange = { patientNote = it },
            placeholder = { Text("Describe how it feels (e.g., warm, stiff...)", color = Color.LightGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            minLines = 3,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFF0F4F8),
                focusedBorderColor = DarkBlue,
                unfocusedContainerColor = Color(0xFFF8FAFC),
                focusedContainerColor = Color(0xFFF8FAFC)
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Warning Disclaimer
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFFFFF7F0) // Matching the light orange tint from design
        ) {
            Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.ErrorOutline, 
                    contentDescription = null, 
                    tint = Color(0xFFB34700), // Darker orange/brown for the icon
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "If you are experiencing severe pain, redness, or heat in the area, please contact your doctor immediately.",
                    fontSize = 15.sp, // Slightly larger font for readability
                    color = Color(0xFFB34700), // Matching the icon color
                    lineHeight = 22.sp
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                com.example.ortho_shoulder_ai.data.SharedSwellingData.uploadedImageUri.value = selectedImageUri
                com.example.ortho_shoulder_ai.data.SharedSwellingData.patientNote.value = patientNote
                com.example.ortho_shoulder_ai.data.SharedSwellingData.painLevel.value = painLevel
                onSubmit()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
        ) {
            Text(text = "Submit for Review", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}
