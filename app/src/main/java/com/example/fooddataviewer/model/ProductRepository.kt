package com.example.fooddataviewer.model

import com.example.fooddataviewer.dto.NutrimentsDto
import com.example.fooddataviewer.dto.ProductDto
import com.example.fooddataviewer.model.database.ProductDao
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

// this class separates layer api from viewModel layer

class ProductRepository @Inject constructor(private val productService: ProductService,
                private val productDao: ProductDao) {

    fun loadProduct(barcode: String): Single<Product>{
        return getProductFromDatabase(barcode)
            .onErrorResumeNext(getProductFromApi(barcode)) // nice thing, if we cant get
        // product from our local database then we make api call
    }

    fun getProductFromDatabase(barcode: String): Single<Product>{
        return productDao.getProduct(barcode)
            .map { product -> mapProduct(product, true) }
    }

    fun getProductFromApi(barcode: String): Single<Product>{
        return productService.getProduct(barcode)
            .map { response ->
                mapProduct(dto = response.product, saved = false)
            }
    }

    fun saveProduct(product: Product): Completable { // type from database class
        return Single.fromCallable { mapProductDto(product) }
            .flatMapCompletable { productDto ->
                productDao.insert(productDto)
            }
    }

    fun deleteProduct(barcode: String): Completable{
        return productDao.delete(barcode)
    }

}

fun mapProduct(dto: ProductDto, saved: Boolean): Product{
    return Product(
        id = dto.id,
        name = dto.product_name,
        brands = dto.brands,
        imageUrl = dto.image_url,
        ingridients = dto.ingridients_text_debug,
        saved = saved,
        nutriments = mapNutriments(dto.nutriments)!!
    )
}

fun mapNutriments(dto: NutrimentsDto?): Nutriments?{
    if (dto == null) return null
        return Nutriments(
            energy = dto.energy_100g,
            salt = dto.salt_100g,
            carbohydrates = dto.carbohydrates_100g,
            fiber = dto.fiber_100g,
            sugars = dto.sugars_100g,
            proteins = dto.proteins_100g,
            fat = dto.fat_100g
        )
}

private fun mapProductDto(product: Product): ProductDto {
    return ProductDto(
        id = product.id,
        product_name = product.name,
        brands = product.brands,
        image_url = product.imageUrl,
        ingridients_text_debug = product.ingridients,
        nutriments = mapNutrimentsDto(product.nutriments)!!
    )
}

fun mapNutrimentsDto(nutriments: Nutriments?): NutrimentsDto?{
    if (nutriments == null) return null
    return NutrimentsDto(
        energy_100g = nutriments.energy,
        salt_100g = nutriments.salt,
        carbohydrates_100g = nutriments.carbohydrates,
        fiber_100g = nutriments.fiber,
        sugars_100g = nutriments.sugars,
        proteins_100g = nutriments.proteins,
        fat_100g = nutriments.fat
    )
}
