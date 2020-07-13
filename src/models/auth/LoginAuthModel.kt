package com.peteralexbizjak.models.auth

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginAuthModel(
    @JsonProperty("email")
    val email: String,

    @JsonProperty("password")
    val password: String
)