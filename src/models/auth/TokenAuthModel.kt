package com.peteralexbizjak.models.auth

import com.fasterxml.jackson.annotation.JsonProperty

data class TokenAuthModel(
    @JsonProperty("successful")
    val wasSuccessful: Boolean,

    @JsonProperty("access_token")
    val accessToken: String
)