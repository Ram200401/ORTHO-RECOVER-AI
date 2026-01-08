package com.example.ortho_shoulder_ai.network.models

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val data: AuthData?
)

data class AuthData(
    val token: String?,
    val user: UserInfo?
)

data class UserInfo(
    val id: Int,
    val name: String,
    val role: String
)

data class RegisterRequest(
    val name: String,
    val email: String?,
    val phone: String?,
    val role: String,
    val password: String,
    val qualification: String? = null,
    val specialization: String? = null,
    val age: Int? = null,
    val gender: String? = null,
    @SerializedName("surgery_type") val surgeryType: String? = null
)

data class LoginRequest(
    val email: String? = null,
    val phone: String? = null,
    val password: String
)
