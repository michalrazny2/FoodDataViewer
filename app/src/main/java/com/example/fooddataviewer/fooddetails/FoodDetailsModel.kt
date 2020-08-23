package com.example.fooddataviewer.fooddetails

import com.example.fooddataviewer.model.Product

data class FoodDetailsModel(val activity: Boolean = false,
                            val product: Product? = null,
                            val error: Boolean = false)