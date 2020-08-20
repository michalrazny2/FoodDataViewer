package com.example.fooddataviewer.dto

import com.squareup.moshi.JsonClass

// i need this class because open food api keeps needed informations in diffrent
// category which is named Nutriments
@JsonClass(generateAdapter = true)
data class NutrimentsDto(
    val energy_100g: Int,
    val salt_100g: Double?, //json field is nullable, so i need to mark it here
    val carbohydrates_100g: Double,
    val fiber_100g: Double?,
    val sugars_100g: Double?,
    val proteins_100g: Double,
    val fat_100g: Double
)