package com.peteralexbizjak.models.tooling

import com.fasterxml.jackson.annotation.JsonProperty

data class NumericUuidModel(
        @JsonProperty("numeric_uuid")
        val numericUuid: String
)