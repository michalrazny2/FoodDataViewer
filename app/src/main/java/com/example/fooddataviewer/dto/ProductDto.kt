package com.example.fooddataviewer.dto

import com.squareup.moshi.JsonClass

//product data transfer object
@JsonClass(generateAdapter = true)
data class ProductDto(
    val id: String,
    val product_name: String,
    val brands: String,
    val image_url: String,
    val ingridients_text_debug: String,
    val nutriments: NutrimentsDto? //name of field has to be exactly same with api
)