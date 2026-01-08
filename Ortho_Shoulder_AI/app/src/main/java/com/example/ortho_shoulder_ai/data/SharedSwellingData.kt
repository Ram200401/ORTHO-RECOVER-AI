package com.example.ortho_shoulder_ai.data

import android.net.Uri
import androidx.compose.runtime.mutableStateOf

object SharedSwellingData {
    var uploadedImageUri = mutableStateOf<Uri?>(null)
    var patientNote = mutableStateOf("")
    var painLevel = mutableStateOf(0f)
}
