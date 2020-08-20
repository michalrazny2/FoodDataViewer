package com.example.fooddataviewer.model

import com.example.fooddataviewer.dto.ProductDto
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response (
    val product: ProductDto
)