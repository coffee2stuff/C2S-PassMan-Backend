package com.peteralexbizjak.models.auth

import com.fasterxml.jackson.annotation.JsonProperty

data class SignUpAuthModel(
    @JsonProperty("display_name")
    val displayName: String,

    @JsonProperty("email")
    val email: String,

    @JsonProperty("password")
    val password: String
)