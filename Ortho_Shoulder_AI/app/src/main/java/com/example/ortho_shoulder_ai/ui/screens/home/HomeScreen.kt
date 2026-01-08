package com.example.ortho_shoulder_ai.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.ortho_shoulder_ai.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue
import com.example.ortho_shoulder_ai.ui.theme.LightBlue
import com.example.ortho_shoulder_ai.data.UserProgressData

@Composable
fun HomeScreen(
    onUploadPhoto: () -> Unit,
    onNavigateToExerciseDetail: (String) -> Unit
) {
    // Read dynamic values from UserProgressData
    val dailyGoalPercentage by UserProgressData.dailyGoalPercentage
    val currentRomDegrees by UserProgressData.currentRomDegrees
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "Wednesday, December 24", fontSize = 14.sp, color = Color.Gray)
                Text(text = "Hello, Sarah", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = DarkBlue)
            }
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = Color(0xFFE0E0E0)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user_avatar),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Stats Row
        Row(modifier = Modifier.fillMaxWidth()) {
            StatsCard(
                title = "Daily Goal",
                value = "$dailyGoalPercentage%",
                icon = Icons.Default.MonitorHeart,
                color = DarkBlue,
                tag = "Today",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            StatsCard(
                title = "Current ROM",
                value = "$currentRomDegrees°",
                icon = Icons.Default.TrendingUp,
                color = LightBlue,
                tag = "Left Shoulder",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Action Required Section
        Text(text = "Action Required", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DarkBlue)
        Spacer(modifier = Modifier.height(16.dp))
        ActionCard(onUploadPhoto = onUploadPhoto)

        Spacer(modifier = Modifier.height(32.dp))

        // Today's Plan
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Today's Plan", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DarkBlue)
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { 
                PlanItem(
                    title = "Pendulum Exercise", 
                    time = "15 mins", 
                    isDone = false,
                    onClick = { onNavigateToExerciseDetail("pendulum") }
                ) 
            }
            item { 
                PlanItem(
                    title = "Wall Push ups", 
                    time = "5 mins", 
                    isDone = false,
                    onClick = { onNavigateToExerciseDetail("wall_pushups") }
                ) 
            }
            item { 
                PlanItem(
                    title = "Cross-Body Stretch", 
                    time = "10 mins", 
                    isDone = false,
                    onClick = { onNavigateToExerciseDetail("cross_body") }
                ) 
            }
            item { 
                PlanItem(
                    title = "Elbow & Wrist", 
                    time = "8 mins", 
                    isDone = false,
                    onClick = { onNavigateToExerciseDetail("elbow_wrist") }
                ) 
            }
        }
    }
}

@Composable
fun StatsCard(title: String, value: String, icon: ImageVector, color: Color, tag: String, modifier: Modifier) {
    Surface(
        modifier = modifier.height(160.dp),
        shape = RoundedCornerShape(24.dp),
        color = color
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White.copy(alpha = 0.2f)
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = Color.White, modifier = Modifier.padding(8.dp))
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White.copy(alpha = 0.2f)
                ) {
                    Text(text = tag, color = Color.White, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(text = value, color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text(text = title, color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
        }
    }
}

@Composable
fun ActionCard(onUploadPhoto: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onUploadPhoto),
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = Color(0xFFFFF3E0)
            ) {
                Icon(imageVector = Icons.Default.ErrorOutline, contentDescription = null, tint = Color(0xFFFF9800), modifier = Modifier.padding(12.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Swelling Check", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkBlue)
                Text(text = "Weekly photo update needed for your physiotherapist.", fontSize = 13.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Upload Photo  >", color = Color(0xFFFF7043), fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun PlanItem(title: String, time: String, isDone: Boolean, onClick: () -> Unit = {}) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFF8F9FA)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = if (isDone) Color(0xFFE8F5E9) else Color.White
            ) {
                Icon(
                    imageVector = if (isDone) Icons.Default.Check else Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = if (isDone) Color(0xFF4CAF50) else Color.Gray,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = if (isDone) Color.Gray else DarkBlue)
                Text(text = time, fontSize = 13.sp, color = Color.Gray)
            }
            if (!isDone) {
                Button(
                    onClick = onClick,
                    modifier = Modifier.height(36.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE3F2FD))
                ) {
                    Text(text = "Start", color = DarkBlue, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.height(80.dp)
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(selectedIconColor = DarkBlue, unselectedIconColor = Color.Gray)
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.Timeline, contentDescription = "Exercises") },
            label = { Text("Exercises", fontSize = 10.sp) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.ShowChart, contentDescription = "Progress") },
            label = { Text("Progress", fontSize = 10.sp) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile", fontSize = 10.sp) }
        )
    }
}
