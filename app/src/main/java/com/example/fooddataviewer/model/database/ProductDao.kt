package com.example.fooddataviewer.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.fooddataviewer.dto.ProductDto
import com.example.fooddataviewer.model.Product
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

// Product Data Access Object
@Dao
abstract class ProductDao {

    @Query("SELECT * FROM ProductDto WHERE id=:barcode")
    abstract fun getProduct(barcode: String): Single<ProductDto>

    @Insert
    abstract fun insert(productDto: ProductDto): Completable

    @Query("DELETE FROM productdto WHERE id=:barcode")
    abstract fun delete(barcode: String): Completable

    @Query("SELECT * FROM ProductDto")
    abstract fun get(): Observable<List<ProductDto>>

}