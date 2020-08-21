package com.example.fooddataviewer.scan

import io.fotoapparat.preview.Frame

sealed class ScanEffect

data class ProcessCameraFrame(val frame: Frame): ScanEffect()

data class ProcessBarcode(val barcode: String): ScanEffect()

data class NavigateToFoodDetails(val barcode: String): ScanEffect()