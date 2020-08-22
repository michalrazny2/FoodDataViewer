package com.example.fooddataviewer.dto

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

//product data transfer object
@Entity  //room annotation
@JsonClass(generateAdapter = true)
data class ProductDto(
    @PrimaryKey val id: String, //when entity we need to define PrimaryKey
    val product_name: String,
    val brands: String,
    val image_url: String,
    val ingridients_text_debug: String?,
    @Embedded val nutriments: NutrimentsDto? //name of field has to be exactly same with api
)