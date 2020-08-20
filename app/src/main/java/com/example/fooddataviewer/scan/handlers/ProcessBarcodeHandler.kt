package com.example.fooddataviewer.scan.handlers

import com.example.fooddataviewer.scan.ProcessBarcode
import com.example.fooddataviewer.scan.ScanEffect
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class ProcessBarcodeHandler @Inject constructor():
    ObservableTransformer<ProcessBarcode, ScanEffect>{
    override fun apply(upstream: Observable<ProcessBarcode>): ObservableSource<ScanEffect> {
        TODO("Not yet implemented")
    }
}