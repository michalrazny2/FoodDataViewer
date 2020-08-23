package com.example.fooddataviewer.foodlist

import com.example.fooddataviewer.model.Product

data class FoodListModel(
    val products: List<Product> = listOf()
)