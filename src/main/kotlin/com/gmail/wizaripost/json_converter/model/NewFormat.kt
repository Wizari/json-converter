package com.gmail.wizaripost.json_converter.model

import com.fasterxml.jackson.annotation.JsonProperty

// Пример нового формата - измените под вашу структуру
data class NewFormat(
    @JsonProperty("id") val id: String,
    @JsonProperty("new_name") val newName: String,
    @JsonProperty("data") val data: NewData,
    @JsonProperty("timestamp") val timestamp: String
)

data class NewData(
    @JsonProperty("value") val value: Double,
    @JsonProperty("items") val items: List<String>
)