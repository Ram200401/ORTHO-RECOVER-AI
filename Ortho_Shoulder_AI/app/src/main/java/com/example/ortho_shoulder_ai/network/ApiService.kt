package com.example.ortho_shoulder_ai.network

import com.example.ortho_shoulder_ai.network.models.*
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // Auth
    @POST("api/auth/register.php")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("api/auth/login.php")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    // Physio
    @GET("api/physio/patients.php")
    suspend fun getPatients(): Response<List<PatientInfo>>

    @POST("api/physio/exercise-plan.php")
    suspend fun createExercisePlan(@Body request: ExercisePlanRequest): Response<Any>

    // Patient
    @POST("api/patient/connect.php")
    suspend fun connectToPhysio(@Body request: ConnectRequest): Response<Any>

    @GET("api/patient/progress.php")
    suspend fun getProgress(): Response<ProgressData>

    @Multipart
    @POST("api/patient/swelling-upload.php")
    suspend fun uploadSwellingImage(
        @Part image: MultipartBody.Part
    ): Response<Any>
}

// Support models for other endpoints
data class PatientInfo(
    val id: Int,
    val name: String,
    val email: String?,
    val phone: String?,
    val age: Int?,
    @SerializedName("surgery_type") val surgeryType: String?,
    val connected: Int
)

data class ExercisePlanRequest(
    @SerializedName("patient_id") val patientId: Int,
    val title: String,
    val description: String?,
    val exercises: List<ExerciseRequest>
)

data class ExerciseRequest(
    val name: String,
    @SerializedName("video_url") val videoUrl: String?,
    val repetitions: String,
    val instructions: String?
)

data class ConnectRequest(
    val code: String
)

data class ProgressData(
    @SerializedName("rom_sessions") val romSessions: List<RomSession>,
    @SerializedName("exercise_logs") val exerciseLogs: List<ExerciseLog>
)

data class RomSession(
    val id: Int,
    @SerializedName("joint_name") val jointName: String,
    @SerializedName("angle_value") val angleValue: Float,
    @SerializedName("session_time") val sessionTime: String
)

data class ExerciseLog(
    val id: Int,
    val name: String,
    val completed: Int,
    @SerializedName("completed_at") val completedAt: String
)
