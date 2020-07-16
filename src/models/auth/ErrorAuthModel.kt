package com.peteralexbizjak.models.auth

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorAuthModel(
    @JsonProperty("code")
    val code: Int,

    @JsonProperty("error")
    val error: String,

    @JsonProperty("desc")
    val description: String
)