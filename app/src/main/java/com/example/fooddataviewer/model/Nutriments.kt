package com.example.fooddataviewer.model

data class Nutriments (
    val energy: Int,
    val salt: Double?, //json field is nullable, so i need to mark it here
    val carbohydrates: Double,
    val fiber: Double?,
    val sugars: Double?,
    val proteins: Double,
    val fat: Double
)