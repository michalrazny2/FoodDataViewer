package com.example.fooddataviewer.fooddetails

import com.example.fooddataviewer.model.Product

sealed class FoodDetailsEffect

data class LoadProduct(val barcode: String) : FoodDetailsEffect()

data class SaveProduct(val product: Product) : FoodDetailsEffect()

data class DeleteProduct(val barcode: String) : FoodDetailsEffect()