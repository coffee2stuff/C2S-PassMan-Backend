package com.peteralexbizjak.models.auth

data class SignUpAuthModel(
    val displayName: String,
    val email: String,
    val password: String
)