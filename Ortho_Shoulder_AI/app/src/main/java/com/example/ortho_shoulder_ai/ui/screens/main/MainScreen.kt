package com.example.ortho_shoulder_ai.ui.screens.main

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ortho_shoulder_ai.ui.navigation.Screen
import com.example.ortho_shoulder_ai.ui.screens.home.HomeScreen
import com.example.ortho_shoulder_ai.ui.screens.exercises.ExercisesScreen
import com.example.ortho_shoulder_ai.ui.screens.progress.ProgressScreen
import com.example.ortho_shoulder_ai.ui.screens.profile.ProfileScreen
import com.example.ortho_shoulder_ai.ui.theme.DarkBlue

@Composable
fun MainScreen(
    onUploadPhoto: () -> Unit,
    onNavigateToPersonalInfo: () -> Unit,
    onNavigateToChangePassword: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToHelpInstructions: () -> Unit,
    onNavigateToAboutApp: () -> Unit,
    onLogout: () -> Unit,
    onNavigateToExerciseDetail: (String) -> Unit
) {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onUploadPhoto = onUploadPhoto,
                    onNavigateToExerciseDetail = onNavigateToExerciseDetail
                )
            }
            composable(Screen.Exercises.route) {
                ExercisesScreen(onNavigateToExerciseDetail = onNavigateToExerciseDetail)
            }
            composable(Screen.Progress.route) {
                ProgressScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    onNavigateToPersonalInfo = onNavigateToPersonalInfo,
                    onNavigateToChangePassword = onNavigateToChangePassword,
                    onNavigateToNotifications = onNavigateToNotifications,
                    onNavigateToHelpInstructions = onNavigateToHelpInstructions,
                    onNavigateToAboutApp = onNavigateToAboutApp,
                    onLogout = onLogout
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: androidx.navigation.NavHostController) {
    val items = listOf(
        NavigationItem("Home", Screen.Home.route, Icons.Default.Home),
        NavigationItem("Exercises", Screen.Exercises.route, Icons.Default.Timeline),
        NavigationItem("Progress", Screen.Progress.route, Icons.Default.ShowChart),
        NavigationItem("Profile", Screen.Profile.route, Icons.Default.Person)
    )
    
    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.height(80.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        
        items.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title, fontSize = 10.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DarkBlue,
                    selectedTextColor = DarkBlue,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color(0xFFE3F2FD)
                )
            )
        }
    }
}

data class NavigationItem(
    val title: String,
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
