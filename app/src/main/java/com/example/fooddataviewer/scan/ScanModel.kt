package com.example.fooddataviewer.scan

import com.example.fooddataviewer.model.Product

data class ScanModel(
    val activity: Boolean = false,
    val processBarcodeResult: ProcessBarcodeResult = ProcessBarcodeResult.Empty
)

// this sealed class can take on of 3 'states'- empty, error(failed loading), and loaded product
sealed class ProcessBarcodeResult{
    object Empty: ProcessBarcodeResult()
    object Error: ProcessBarcodeResult()
    data class ProductLoaded(val product: Product): ProcessBarcodeResult()
}