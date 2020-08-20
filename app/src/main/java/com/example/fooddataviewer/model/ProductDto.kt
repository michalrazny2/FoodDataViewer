package com.example.fooddataviewer.model

import com.squareup.moshi.JsonClass

//product data transfer object
@JsonClass(generateAdapter = true)
data class ProductDto(
    val product_name: String,
    val brands: String,
    val image_url: String,
    val ingridients_text_debug: String
)