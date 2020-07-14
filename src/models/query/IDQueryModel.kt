package com.peteralexbizjak.models.query

import com.fasterxml.jackson.annotation.JsonProperty

data class IDQueryModel(
    @JsonProperty("id")
    val documentID: String
)