package com.example.ortho_shoulder_ai.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object SurgerySelection : Screen("surgery_selection")
    object SurgeryDetails : Screen("surgery_details")
    object AIFeedback : Screen("ai_feedback")
    object Home : Screen("home")
    object Exercises : Screen("exercises")
    object Progress : Screen("progress")
    object Profile : Screen("profile")
    object SwellingCheck : Screen("swelling_check")
    object UploadSuccess : Screen("upload_success")
    object PersonalInfo : Screen("personal_info")
    object ChangePassword : Screen("change_password")
    object Notifications : Screen("notifications")
    object HelpInstructions : Screen("help_instructions")
    object ExerciseDetail : Screen("exercise_detail/{exerciseId}") {
        fun createRoute(exerciseId: String) = "exercise_detail/$exerciseId"
    }
    object ROMTracking : Screen("rom_tracking/{exerciseName}") {
        fun createRoute(exerciseName: String) = "rom_tracking/$exerciseName"
    }
    object ExerciseSession : Screen("exercise_session/{exerciseName}") {
        fun createRoute(exerciseName: String) = "exercise_session/$exerciseName"
    }
    object SessionComplete : Screen("session_complete/{exerciseName}/{accuracy}/{rom}") {
        fun createRoute(exerciseName: String, accuracy: Int, rom: Int) = 
            "session_complete/$exerciseName/$accuracy/$rom"
    }
    object ClinicianLogin : Screen("clinician_login")
    object ClinicianDashboard : Screen("clinician_dashboard")
    object ClinicianForgotPassword : Screen("clinician_forgot_password")
    object ClinicianPatientReview : Screen("clinician_patient_review")
    object ClinicianSwellingReview : Screen("clinician_swelling_review")
    object ClinicianAssessment : Screen("clinician_assessment")
    object ClinicianAssessmentSuccess : Screen("clinician_assessment_success")
    object ClinicianSignUp : Screen("clinician_signup")
    object ForgotPassword : Screen("forgot_password")
    object AboutApp : Screen("about_app")
    object ClinicianPatientDetail : Screen("clinician_patient_detail/{patientId}") {
        fun createRoute(patientId: String) = "clinician_patient_detail/$patientId"
    }
    object AddPatient : Screen("add_patient")
    object ClinicianPersonalInfo : Screen("clinician_personal_info")
    object ClinicianChangePassword : Screen("clinician_change_password")
    object ClinicianNotifications : Screen("clinician_notifications")
    object ClinicianAboutApp : Screen("clinician_about_app")
    object ClinicianPatientProgress : Screen("clinician_patient_progress/{patientId}") {
        fun createRoute(patientId: String) = "clinician_patient_progress/$patientId"
    }
    object ClinicianSuggestion : Screen("clinician_suggestion/{patientId}") {
        fun createRoute(patientId: String) = "clinician_suggestion/$patientId"
    }
    object ClinicianSuggestionSuccess : Screen("clinician_suggestion_success")
    object ContactPhysio : Screen("contact_physio")
}






