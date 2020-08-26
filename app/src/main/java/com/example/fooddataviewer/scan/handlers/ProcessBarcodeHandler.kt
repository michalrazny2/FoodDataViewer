package com.example.fooddataviewer.scan.handlers

import android.util.Log
import com.example.fooddataviewer.model.ProductRepository
import com.example.fooddataviewer.model.mapProduct
import com.example.fooddataviewer.scan.*
import com.example.fooddataviewer.utils.IdlingResource
import com.google.android.gms.vision.barcode.Barcode
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProcessBarcodeHandler @Inject constructor(
    private val productRepository: ProductRepository,
    private val idlingResource: IdlingResource
    ):
    ObservableTransformer<ProcessBarcode, ScanEvent>{

    override fun apply(upstream: Observable<ProcessBarcode>): ObservableSource<ScanEvent> { //todo tutaj dosc na slepo zmienione ze ScanEffect
        return upstream.switchMap { effect ->
            productRepository.getProductFromApi(effect.barcode).map {
                product ->
                idlingResource.decrement() //needed for instrumented tests
                ProductLoaded(product) as ScanEvent
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { error -> Log.e("ProcessBarcode", error.message, error) }
                .onErrorReturn { BarcodeError }
                .toObservable()
        }
    }
}