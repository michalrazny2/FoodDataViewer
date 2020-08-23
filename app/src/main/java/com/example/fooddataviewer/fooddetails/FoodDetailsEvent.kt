package com.example.fooddataviewer.fooddetails

import com.example.fooddataviewer.model.Product

sealed class FoodDetailsEvent

object ActionButtonClicked: FoodDetailsEvent()
data class Initial(val barcode: String): FoodDetailsEvent()
data class ProductLoaded(val product: Product): FoodDetailsEvent()
object ErrorLoadingProduct : FoodDetailsEvent()