package com.example.fooddataviewer.foodlist

sealed class FoodListEffect

object NavigateToScanner: FoodListEffect()
object ObserveProducts: FoodListEffect()
data class NavigateToFoodDetails(val barcode: String) : FoodListEffect()