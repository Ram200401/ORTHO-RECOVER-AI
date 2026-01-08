package com.example.ortho_shoulder_ai.ui.screens.clinician

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ortho_shoulder_ai.R
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue
import com.example.ortho_shoulder_ai.data.PatientStateManager
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun ClinicianDashboardScreen(
    onLogout: () -> Unit,
    onNavigateToPatientDetail: (String) -> Unit,
    onNavigateToAddPatient: () -> Unit,
    onNavigateToPersonalInfo: () -> Unit,
    onNavigateToChangePassword: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToAboutApp: () -> Unit
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    var selectedSurgery by rememberSaveable { mutableStateOf<String?>(null) }

    Scaffold(
        bottomBar = {
            ClinicianBottomBar(
                selectedIndex = selectedTab,
                onItemSelected = { 
                    selectedTab = it 
                }
            )
        },
        floatingActionButton = {
            if (selectedTab == 1 && selectedSurgery == null) {
                FloatingActionButton(
                    onClick = onNavigateToAddPatient,
                    containerColor = DarkBlue,
                    contentColor = Color.White,
                    shape = CircleShape,
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Patient")
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
        ) {
            when (selectedTab) {
                0 -> ClinicianHomeContent()
                1 -> {
                    if (selectedSurgery == null) {
                        BackHandler {
                            selectedTab = 0
                        }
                        ClinicianSurgerySelectionContent(
                            onBack = { selectedTab = 0 },
                            onSurgerySelected = { selectedSurgery = it }
                        )
                    } else {
                        BackHandler {
                            selectedSurgery = null
                        }
                        ClinicianPatientsContent(
                            surgeryName = selectedSurgery!!,
                            searchQuery = searchQuery,
                            onSearchQueryChange = { searchQuery = it },
                            onBack = { selectedSurgery = null },
                            onPatientClick = { onNavigateToPatientDetail(it) }
                        )
                    }
                }
                2 -> ClinicianProfileContent(
                    onLogout = onLogout,
                    onNavigateToPersonalInfo = onNavigateToPersonalInfo,
                    onNavigateToChangePassword = onNavigateToChangePassword,
                    onNavigateToNotifications = onNavigateToNotifications,
                    onNavigateToAboutApp = onNavigateToAboutApp
                )
            }
        }
    }
}

@Composable
fun ClinicianHomeContent() {
    val needsAttentionCount by PatientStateManager.needsAttentionCount.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        // Header: Dr. Nidhi
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(
                    text = "Dr. Nidhi",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )
                Text(
                    text = "OrthoCare Clinic • 10 Active Patients",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
            Image(
                painter = painterResource(id = R.drawable.user_avatar),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE2E8F0)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Search Bar removed from Home tab per request

        Spacer(modifier = Modifier.height(32.dp))

        // Hero Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SummaryCard(
                icon = Icons.Default.Groups,
                title = "Active Patients",
                count = "10",
                tag = "Total",
                backgroundColor = DarkBlue,
                modifier = Modifier
                    .weight(1f)
            )
            SummaryCard(
                icon = Icons.Default.Warning,
                title = "Needs Attention",
                count = needsAttentionCount.toString(),
                tag = "Priority",
                backgroundColor = Color(0xFFF59E0B), // Orange/Amber
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Recent Activity Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Activity",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkBlue
            )
            TextButton(onClick = { /* See All */ }) {
                Text(
                    text = "See All",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkBlue.copy(alpha = 0.6f)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        
        RecentActivityList()
    }
}

@Composable
fun ClinicianSurgerySelectionContent(
    onBack: () -> Unit,
    onSurgerySelected: (String) -> Unit
) {
    val surgeries = listOf(
        "Rotator Cuff Repair" to "Repair of torn tendons in the shoulder",
        "Shoulder Arthroscopy" to "Minimally invasive joint procedure",
        "Impingement Surgery" to "Decompression of shoulder space"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = DarkBlue)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Select Surgery",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )
                Text(
                    text = "View patients based on their procedure.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(surgeries.size) { index ->
                val (title, description) = surgeries[index]
                SurgerySelectionCard(
                    title = title,
                    description = description,
                    onClick = { onSurgerySelected(title) }
                )
            }
        }
    }
}

@Composable
fun SurgerySelectionCard(title: String, description: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFF8FAFC),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color.White
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.MedicalServices,
                        contentDescription = null,
                        tint = DarkBlue,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )
                Text(
                    text = description,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun ClinicianPatientsContent(
    surgeryName: String,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onBack: () -> Unit,
    onPatientClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = DarkBlue)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Patients",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )
                Text(
                    text = surgeryName,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        SearchField(searchQuery, onSearchQueryChange)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            val patients = com.example.ortho_shoulder_ai.data.model.PatientRepository.getPatientsForSurgery(surgeryName)
            
            items(patients.size) { index ->
                val patient = patients[index]
                PatientListItem(
                    name = patient.name,
                    age = patient.age,
                    gender = patient.gender,
                    patientId = patient.id,
                    statusColor = if (index % 3 == 0) Color(0xFF10B981) else Color(0xFF3B82F6),
                    onClick = { onPatientClick(patient.id) }
                )
            }
        }
    }
}

@Composable
fun PatientListItem(
    name: String,
    age: Int,
    gender: String,
    patientId: String,
    statusColor: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFF8FAFC),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray)
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = name,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkBlue
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "#$patientId",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                }
                Text(
                    text = "$age yrs • $gender",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
            
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(statusColor)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color(0xFFCBD5E1),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun ClinicianProfileContent(
    onLogout: () -> Unit,
    onNavigateToPersonalInfo: () -> Unit,
    onNavigateToChangePassword: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToAboutApp: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        // Profile Header
        Text(
            text = "Profile",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBlue
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // User Info Section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE2E8F0)),
                contentAlignment = Alignment.Center
            ) {
                // Image or Initial
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    text = "Dr. Nidhi",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )
                Text(
                    text = "Profile ID: #88392",
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }
        }
        
        Spacer(modifier = Modifier.height(40.dp))
        
        // Menu Items
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ProfileMenuItem(
                icon = Icons.Outlined.Person,
                label = "Personal Information",
                onClick = onNavigateToPersonalInfo
            )
            ProfileMenuItem(
                icon = Icons.Outlined.Lock,
                label = "Change Password",
                onClick = onNavigateToChangePassword
            )
            ProfileMenuItem(
                icon = Icons.Outlined.Notifications,
                label = "Notifications",
                onClick = onNavigateToNotifications
            )
            ProfileMenuItem(
                icon = Icons.Outlined.Info,
                label = "About App",
                onClick = onNavigateToAboutApp
            )
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Logout Button
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
                .clickable { onLogout() },
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFFEF2F2) // Light red
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = null,
                    tint = Color(0xFFEF4444), // Red
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Log Out",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFEF4444)
                )
            }
        }
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF1F5F9))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = Color(0xFFF0F7FF) // Very light blue
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color(0xFF3B82F6), // Blue
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = DarkBlue,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color(0xFFCBD5E1),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun SearchField(query: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search...", color = Color.LightGray, fontSize = 15.sp) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = DarkBlue.copy(alpha = 0.2f),
            unfocusedContainerColor = Color(0xFFF8FAFC),
            focusedContainerColor = Color(0xFFF8FAFC)
        ),
        singleLine = true
    )
}

@Composable
fun RecentActivityList() {
    val activities = listOf(
        ReviewItemData("John Doe", "Shoulder Flexion • 5 mins ago", Color(0xFF10B981)),
        ReviewItemData("Alice Smith", "ROM Session • 1 hour ago", Color(0xFFF59E0B)),
        ReviewItemData("Tom Wilson", "Swelling Photo • 3 hours ago", Color(0xFF3B82F6))
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        activities.forEach { activity ->
            PatientActivityItem(activity)
        }
    }
}

@Composable
fun PatientActivityItem(activity: ReviewItemData) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFF8FAFC)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar Placeholder
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE2E8F0)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray)
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = activity.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )
                Text(
                    text = activity.details,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

data class ReviewItemData(val name: String, val details: String, val statusColor: Color)

@Composable
fun SummaryCard(
    icon: ImageVector,
    title: String,
    count: String,
    tag: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.height(160.dp),
        shape = RoundedCornerShape(24.dp),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                Surface(
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = tag,
                        color = Color.White,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            Column {
                Text(text = count, color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold)
                Text(text = title, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun ClinicianBottomBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val items = listOf(
        BottomNavItem("Home", Icons.Outlined.Home, Icons.Filled.Home),
        BottomNavItem("Patients", Icons.Outlined.Groups, Icons.Filled.Groups),
        BottomNavItem("Profile", Icons.Outlined.Person, Icons.Filled.Person)
    )

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 16.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = index == selectedIndex
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable { onItemSelected(index) }
                        .padding(top = 0.dp)
                        .weight(1f)
                ) {
                    // Top Indicator Line
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp))
                            .background(if (isSelected) DarkBlue else Color.Transparent)
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label,
                        tint = if (isSelected) DarkBlue else Color(0xFF94A3B8),
                        modifier = Modifier.size(24.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = item.label,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (isSelected) DarkBlue else Color(0xFF94A3B8)
                    )
                }
            }
        }
    }
}

data class BottomNavItem(
    val label: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)
