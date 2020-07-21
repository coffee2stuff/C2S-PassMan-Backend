package com.peteralexbizjak.models.database

import com.fasterxml.jackson.annotation.JsonProperty

data class AllDatabaseItemsModel(
        @JsonProperty("doc_type")
        val documentType: Int,

        @JsonProperty("doc_id")
        val documentID: String,

        @JsonProperty("title")
        val title: String,

        @JsonProperty("desc")
        val description: String
)