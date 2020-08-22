package com.example.fooddataviewer.fooddetails

sealed class FoodDetailsEvent

object ActionButtonClicked: FoodDetailsEvent()
data class Initial(val barcode: String): FoodDetailsEvent()