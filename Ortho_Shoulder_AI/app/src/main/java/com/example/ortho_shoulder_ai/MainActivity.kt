package com.example.ortho_shoulder_ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.ortho_shoulder_ai.ui.navigation.AppNavigation
import com.example.ortho_shoulder_ai.ui.theme.Ortho_Shoulder_AITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen and keep it on screen for 0ms (effectively hiding it)
        installSplashScreen().setKeepOnScreenCondition { false }
        
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ortho_Shoulder_AITheme {
                AppNavigation()
            }
        }
    }
}
