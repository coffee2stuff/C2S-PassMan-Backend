package com.peteralexbizjak.models.auth

data class ErrorAuthModel(
    val code: Int,
    val error: String,
    val description: String
)