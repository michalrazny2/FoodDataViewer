package com.example.fooddataviewer.foodlist

import com.example.fooddataviewer.model.Product

sealed class FoodListEvent

object AddButtonClicked : FoodListEvent()
object Initial: FoodListEvent()
data class ProductsLoaded(val products: List<Product>): FoodListEvent()
data class ProductClicked(val barcode: String): FoodListEvent()