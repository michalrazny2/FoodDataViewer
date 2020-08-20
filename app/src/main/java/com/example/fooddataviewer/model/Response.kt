package com.example.fooddataviewer.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response (
    val product: ProductDto
)