package com.example.ortho_shoulder_ai.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.ortho_shoulder_ai.ui.screens.splash.SplashScreen
import com.example.ortho_shoulder_ai.ui.screens.welcome.WelcomeScreen
import com.example.ortho_shoulder_ai.ui.screens.login.LoginScreen
import com.example.ortho_shoulder_ai.ui.screens.login.ForgotPasswordScreen
import com.example.ortho_shoulder_ai.ui.screens.signup.SignUpScreen
import com.example.ortho_shoulder_ai.ui.screens.surgery.SurgerySelectionScreen
import com.example.ortho_shoulder_ai.ui.screens.surgery.SurgeryDetailsScreen
import com.example.ortho_shoulder_ai.ui.screens.info.AIFeedbackScreen
import com.example.ortho_shoulder_ai.ui.screens.info.UploadSuccessScreen
import com.example.ortho_shoulder_ai.ui.screens.main.MainScreen
import com.example.ortho_shoulder_ai.ui.screens.check.SwellingCheckScreen
import com.example.ortho_shoulder_ai.ui.screens.profile.PersonalInfoScreen
import com.example.ortho_shoulder_ai.ui.screens.profile.ChangePasswordScreen
import com.example.ortho_shoulder_ai.ui.screens.profile.NotificationsScreen
import com.example.ortho_shoulder_ai.ui.screens.profile.HelpInstructionsScreen
import com.example.ortho_shoulder_ai.ui.screens.profile.AboutAppScreen
import com.example.ortho_shoulder_ai.ui.screens.exercises.ExerciseDetailScreen
import com.example.ortho_shoulder_ai.ui.screens.exercises.ROMTrackingScreen
import com.example.ortho_shoulder_ai.ui.screens.exercises.ExerciseSessionScreen
import com.example.ortho_shoulder_ai.ui.screens.exercises.SessionCompleteScreen
import com.example.ortho_shoulder_ai.ui.screens.clinician.ClinicianLoginScreen
import com.example.ortho_shoulder_ai.ui.screens.clinician.ClinicianDashboardScreen
import com.example.ortho_shoulder_ai.ui.screens.clinician.ClinicianForgotPasswordScreen
import com.example.ortho_shoulder_ai.ui.screens.clinician.ClinicianPatientProgressScreen
import com.example.ortho_shoulder_ai.ui.screens.clinician.ClinicianPatientReviewScreen
import com.example.ortho_shoulder_ai.ui.screens.clinician.ClinicianSwellingReviewScreen
import com.example.ortho_shoulder_ai.ui.screens.clinician.ClinicianAssessmentScreen
import com.example.ortho_shoulder_ai.ui.screens.clinician.ClinicianAssessmentSuccessScreen
import com.example.ortho_shoulder_ai.ui.screens.clinician.ClinicianSignUpScreen
import com.example.ortho_shoulder_ai.ui.screens.clinician.ClinicianPatientDetailScreen
import com.example.ortho_shoulder_ai.ui.screens.clinician.AddPatientScreen
import com.example.ortho_shoulder_ai.ui.screens.clinician.ClinicianPersonalInfoScreen
import com.example.ortho_shoulder_ai.ui.screens.clinician.ClinicianChangePasswordScreen
import com.example.ortho_shoulder_ai.ui.screens.clinician.ClinicianNotificationsScreen
import com.example.ortho_shoulder_ai.ui.screens.clinician.ClinicianAboutAppScreen
import com.example.ortho_shoulder_ai.ui.screens.clinician.ClinicianSuggestionScreen
import com.example.ortho_shoulder_ai.ui.screens.clinician.ClinicianSuggestionSuccessScreen
import com.example.ortho_shoulder_ai.ui.screens.contact.ContactPhysioScreen



@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // ... (onboarding flows remain same)
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToNext = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },
                onNavigateToSignUp = {
                    navController.navigate(Screen.SignUp.route)
                },
                onNavigateToClinicianLogin = {
                    navController.navigate(Screen.ClinicianLogin.route)
                }
            )
        }
        composable(Screen.ClinicianLogin.route) {
            ClinicianLoginScreen(
                onBack = { navController.popBackStack() },
                onLoginSuccess = {
                    navController.navigate(Screen.ClinicianDashboard.route) {
                        popUpTo(Screen.ClinicianLogin.route) { inclusive = true }
                    }
                },
                onNavigateToForgotPassword = {
                    navController.navigate(Screen.ClinicianForgotPassword.route)
                },
                onSignUp = {
                    navController.navigate(Screen.ClinicianSignUp.route)
                }
            )
        }
        composable(Screen.ClinicianForgotPassword.route) {
            ClinicianForgotPasswordScreen(
                onBack = { navController.popBackStack() },
                onResetSuccess = {
                    navController.popBackStack(Screen.ClinicianLogin.route, inclusive = false)
                }
            )
        }
        composable(Screen.ClinicianSignUp.route) {
            ClinicianSignUpScreen(
                onBack = { navController.popBackStack() },
                onNavigateToLogin = {
                    navController.navigate(Screen.ClinicianLogin.route) {
                        popUpTo(Screen.ClinicianSignUp.route) { inclusive = true }
                    }
                },
                onSignUpSuccess = {
                    navController.navigate(Screen.ClinicianLogin.route) {
                        popUpTo(Screen.ClinicianSignUp.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.ClinicianDashboard.route) {
            ClinicianDashboardScreen(
                onLogout = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(0)
                    }
                },
                onNavigateToPatientDetail = { patientId ->
                    navController.navigate(Screen.ClinicianPatientDetail.createRoute(patientId))
                },
                onNavigateToAddPatient = {
                    navController.navigate(Screen.AddPatient.route)
                },
                onNavigateToPersonalInfo = {
                    navController.navigate(Screen.ClinicianPersonalInfo.route)
                },
                onNavigateToChangePassword = {
                    navController.navigate(Screen.ClinicianChangePassword.route)
                },
                onNavigateToNotifications = {
                    navController.navigate(Screen.ClinicianNotifications.route)
                },
                onNavigateToAboutApp = {
                    navController.navigate(Screen.ClinicianAboutApp.route)
                }
            )
        }

        composable(Screen.ClinicianPatientReview.route) {
            ClinicianPatientReviewScreen(
                onBack = { navController.popBackStack() },
                onNavigateToSwellingReview = {
                    navController.navigate(Screen.ClinicianSwellingReview.route)
                }
            )
        }
        composable(Screen.ClinicianSwellingReview.route) {
            ClinicianSwellingReviewScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.ClinicianAssessment.route) {
            ClinicianAssessmentScreen(
                onBack = { navController.popBackStack() },
                onSendSuccess = {
                    navController.navigate(Screen.ClinicianAssessmentSuccess.route)
                }
            )
        }
        composable(Screen.ClinicianAssessmentSuccess.route) {
            ClinicianAssessmentSuccessScreen(
                onReturnToDashboard = {
                    navController.navigate(Screen.ClinicianDashboard.route) {
                        popUpTo(Screen.ClinicianDashboard.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.ClinicianPatientDetail.route) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId") ?: ""
            ClinicianPatientDetailScreen(
                patientId = patientId,
                onBack = { navController.popBackStack() },
                onNavigateToProgress = { pid ->
                    navController.navigate(Screen.ClinicianPatientProgress.createRoute(pid))
                },
                onNavigateToSuggestion = { pid ->
                    navController.navigate(Screen.ClinicianSuggestion.createRoute(pid))
                },
                onNavigateToSwelling = { pid ->
                    navController.navigate(Screen.ClinicianSwellingReview.route)
                }
            )
        }
        composable(Screen.AddPatient.route) {
            AddPatientScreen(
                onBack = { navController.popBackStack() },
                onPatientAdded = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.ClinicianPersonalInfo.route) {
            ClinicianPersonalInfoScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.ClinicianChangePassword.route) {
            ClinicianChangePasswordScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.ClinicianNotifications.route) {
            ClinicianNotificationsScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.ClinicianAboutApp.route) {
            ClinicianAboutAppScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onBack = { navController.popBackStack() },
                onNavigateToSignUp = {
                    navController.navigate(Screen.SignUp.route)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(Screen.ForgotPassword.route)
                },
                onLoginSuccess = {
                    navController.navigate(Screen.SurgerySelection.route)
                }
            )
        }
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onBack = { navController.popBackStack() },
                onResetSuccess = {
                    navController.popBackStack(Screen.Login.route, inclusive = false)
                }
            )
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(
                onBack = { navController.popBackStack() },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },
                onSignUpSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.SignUp.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.SurgerySelection.route) {
            SurgerySelectionScreen(
                onBack = { navController.popBackStack() },
                onSurgerySelected = { surgery ->
                    navController.navigate(Screen.SurgeryDetails.route)
                }
            )
        }
        composable(Screen.SurgeryDetails.route) {
            SurgeryDetailsScreen(
                onBack = { navController.popBackStack() },
                onSaveAndContinue = {
                    navController.navigate(Screen.AIFeedback.route)
                }
            )
        }
        composable(Screen.AIFeedback.route) {
            AIFeedbackScreen(
                onContinue = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        // Use MainScreen for the top-level app experience
        composable(Screen.Home.route) {
            MainScreen(
                onUploadPhoto = {
                    navController.navigate(Screen.SwellingCheck.route)
                },
                onNavigateToPersonalInfo = {
                    navController.navigate(Screen.PersonalInfo.route)
                },
                onNavigateToChangePassword = {
                    navController.navigate(Screen.ChangePassword.route)
                },
                onNavigateToNotifications = {
                    navController.navigate(Screen.Notifications.route)
                },
                onNavigateToHelpInstructions = {
                    navController.navigate(Screen.HelpInstructions.route)
                },
                onNavigateToAboutApp = {
                    navController.navigate(Screen.AboutApp.route)
                },
                onLogout = {
                    navController.navigate(Screen.Splash.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onNavigateToExerciseDetail = { exerciseId ->
                    navController.navigate(Screen.ExerciseDetail.createRoute(exerciseId))
                }
            )
        }
        composable(Screen.SwellingCheck.route) {
            SwellingCheckScreen(
                onBack = { navController.popBackStack() },
                onSubmit = {
                    navController.navigate(Screen.UploadSuccess.route)
                }
            )
        }
        composable(Screen.UploadSuccess.route) {
            UploadSuccessScreen(
                onBackToDashboard = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.PersonalInfo.route) {
            PersonalInfoScreen(
                onBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() }
            )
        }
        composable(Screen.ChangePassword.route) {
            ChangePasswordScreen(
                onBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() }
            )
        }
        composable(Screen.Notifications.route) {
            NotificationsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.HelpInstructions.route) {
            HelpInstructionsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AboutApp.route) {
            AboutAppScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.ExerciseDetail.route) { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getString("exerciseId") ?: ""
            ExerciseDetailScreen(
                exerciseId = exerciseId,
                onBack = { navController.popBackStack() },
                onStartTracking = { exerciseName ->
                    navController.navigate(Screen.ROMTracking.createRoute(exerciseName))
                }
            )
        }
        composable(Screen.ROMTracking.route) { backStackEntry ->
            val exerciseName = backStackEntry.arguments?.getString("exerciseName") ?: ""
            ROMTrackingScreen(
                exerciseName = exerciseName,
                onBack = { navController.popBackStack() },
                onReady = {
                    navController.navigate(Screen.ExerciseSession.createRoute(exerciseName))
                }
            )
        }
        composable(Screen.ExerciseSession.route) { backStackEntry ->
            val exerciseName = backStackEntry.arguments?.getString("exerciseName") ?: ""
            ExerciseSessionScreen(
                exerciseName = exerciseName,
                onBack = { navController.popBackStack() },
                onSessionComplete = { name, accuracy, rom ->
                    navController.navigate(Screen.SessionComplete.createRoute(name, accuracy, rom)) {
                        popUpTo(Screen.ExerciseSession.route) { inclusive = true }
                    }
                },
                onContactPhysio = {
                    navController.navigate(Screen.ContactPhysio.route)
                }
            )
        }
        composable(Screen.ContactPhysio.route) {
            ContactPhysioScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.SessionComplete.route) { backStackEntry ->
            val exerciseName = backStackEntry.arguments?.getString("exerciseName") ?: ""
            val accuracy = backStackEntry.arguments?.getString("accuracy")?.toIntOrNull() ?: 0
            val rom = backStackEntry.arguments?.getString("rom")?.toIntOrNull() ?: 0
            SessionCompleteScreen(
                exerciseName = exerciseName,
                accuracy = accuracy,
                rom = rom,
                onBackToDashboard = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onRepeatSession = {
                    navController.navigate(Screen.ROMTracking.createRoute(exerciseName)) {
                        popUpTo(Screen.SessionComplete.route) { inclusive = true }
                    }
                }
            )
        }



        composable(
            route = Screen.ClinicianPatientProgress.route,
            arguments = listOf(navArgument("patientId") { type = NavType.StringType })
        ) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId") ?: return@composable
            ClinicianPatientProgressScreen(
                patientId = patientId,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.ClinicianSuggestion.route,
            arguments = listOf(navArgument("patientId") { type = NavType.StringType })
        ) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId") ?: return@composable
            ClinicianSuggestionScreen(
                patientId = patientId,
                onBack = { navController.popBackStack() },
                onSend = {
                    navController.navigate(Screen.ClinicianSuggestionSuccess.route) {
                        popUpTo(Screen.ClinicianSuggestion.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.ClinicianSuggestionSuccess.route) {
            ClinicianSuggestionSuccessScreen(
                onReturnToPatient = {
                    navController.popBackStack()
                }
            )
        }
    }
}
