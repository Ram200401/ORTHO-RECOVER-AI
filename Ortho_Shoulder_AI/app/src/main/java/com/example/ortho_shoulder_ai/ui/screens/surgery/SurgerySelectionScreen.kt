package com.example.ortho_shoulder_ai.ui.screens.surgery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.SettingsAccessibility
import androidx.compose.material.icons.filled.Vaccines
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue
import com.example.ortho_shoulder_ai.ui.theme.LightBlue

data class SurgeryOption(
    val title: String,
    val description: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurgerySelectionScreen(
    onBack: () -> Unit,
    onSurgerySelected: (SurgeryOption) -> Unit
) {
    val surgeries = listOf(
        SurgeryOption(
            "Rotator Cuff Repair",
            "Repair of torn tendons in the shoulder",
            Icons.Default.MonitorHeart
        ),
        SurgeryOption(
            "Shoulder Arthroscopy",
            "Minimally invasive joint procedure",
            Icons.Default.SettingsAccessibility
        ),
        SurgeryOption(
            "Impingement Surgery",
            "Decompression of shoulder space",
            Icons.Default.Vaccines
        )
    )

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
            text = "Select Surgery",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBlue
        )
        Text(
            text = "Choose the procedure you are recovering from.",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(surgeries) { surgery ->
                SurgeryCard(surgery = surgery, onClick = { onSurgerySelected(surgery) })
            }
        }
    }
}

@Composable
fun SurgeryCard(surgery: SurgeryOption, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(56.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF0F7FF)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = surgery.icon,
                        contentDescription = null,
                        tint = LightBlue,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column {
                Text(
                    text = surgery.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )
                Text(
                    text = surgery.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
