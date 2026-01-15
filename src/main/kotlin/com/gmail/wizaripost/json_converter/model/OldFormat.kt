package com.gmail.wizaripost.json_converter.model

import com.fasterxml.jackson.annotation.JsonProperty

// Пример старого формата - измените под вашу структуру
data class OldFormat(
    @JsonProperty("identifier") val identifier: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("value") val value: Double,
    @JsonProperty("items") val items: List<String>,
    @JsonProperty("created_at") val createdAt: String
)