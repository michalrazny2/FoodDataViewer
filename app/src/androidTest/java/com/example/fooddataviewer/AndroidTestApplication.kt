package com.example.fooddataviewer

import com.example.fooddataviewer.di.DaggerTestComponent
import com.example.fooddataviewer.dto.ProductDto
import com.example.fooddataviewer.model.database.ProductDao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

class AndroidTestApplication: App(){

    val productDtoSubject = PublishSubject.create<List<ProductDto>>()
    private val productDao: ProductDao = object : ProductDao(){
        override fun getProduct(barcode: String): Single<ProductDto> {
            throw NotImplementedError("Not implemented in instrumented")
        }

        override fun insert(productDto: ProductDto): Completable {
            throw NotImplementedError("Not implemented in instrumented")
        }

        override fun delete(barcode: String): Completable {
            throw NotImplementedError("Not implemented in instrumented")
        }

        override fun get(): Observable<List<ProductDto>> {
            return productDtoSubject
        }

    }

    override val component by lazy{
        DaggerTestComponent.builder()
            .context(this)
            .productDao(productDao)
            .build()
    }
}