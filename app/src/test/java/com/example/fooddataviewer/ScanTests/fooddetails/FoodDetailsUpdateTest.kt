package com.example.fooddataviewer.ScanTests.fooddetails

import com.example.fooddataviewer.fooddetails.*
import com.example.fooddataviewer.model.Product
import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Before
import org.junit.Test

// I will use this class to test update function from FoodDetailsViewModel

class FoodDetailsUpdateTest {

    private lateinit var  updateSpec: UpdateSpec<FoodDetailsModel, FoodDetailsEvent, FoodDetailsEffect>

    @Before
    fun before(){
        updateSpec = UpdateSpec(::foodDetailsUpdate)
    }

    @Test
    fun initialEvent_activityTrue_loadProductDispatched(){
        val model = FoodDetailsModel()
        val barcode = "111"

        updateSpec.given(model)
            .whenEvent(Initial(barcode))
            .then(
                assertThatNext<FoodDetailsModel, FoodDetailsEffect>(
                    hasModel(model.copy(activity=true)),
                    hasEffects(LoadProduct(barcode))
                )
            )
    }

    @Test
    fun productLoaded_activityFalseProduct(){
        val model = FoodDetailsModel()

        val product = Product(
            id = "someId",
            saved = false,
            name = "product",
            brands = "brand",
            imageUrl = "www.something.pl",
            ingridients = "sugar",
            nutriments = null
        )
        updateSpec.given(model)
            .whenEvent(ProductLoaded(product))
            .then(
                assertThatNext<FoodDetailsModel, FoodDetailsEffect>(
                    hasModel(model.copy(activity=false, product = product)),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun errorLoadingProduct_activityFalseError(){
        val model = FoodDetailsModel()

        updateSpec.given(model)
            .whenEvent(ErrorLoadingProduct)
            .then(
                assertThatNext<FoodDetailsModel, FoodDetailsEffect>(
                    hasModel(model.copy(activity=false, error = true)),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun actionButtonClicked_deleteProductDispatched(){

        val product = Product(
            id = "someId",
            saved = true,
            name = "product",
            brands = "brand",
            imageUrl = "www.something.pl",
            ingridients = "sugar",
            nutriments = null
        )

        val model = FoodDetailsModel(product = product)

        updateSpec.given(model)
            .whenEvent(ActionButtonClicked)
            .then(
                assertThatNext<FoodDetailsModel, FoodDetailsEffect>(
                    hasNoModel(),
                    hasEffects(DeleteProduct(product.id))
                )
            )
    }

    @Test
    fun actionButtonClicked_saveProductDispatched(){
        val product = Product(
            id = "someId",
            saved = true,
            name = "product",
            brands = "brand",
            imageUrl = "www.something.pl",
            ingridients = "sugar",
            nutriments = null
        )
        val model = FoodDetailsModel(product = product)

        updateSpec.given(model)
            .whenEvent(ActionButtonClicked)
            .then(
                assertThatNext<FoodDetailsModel, FoodDetailsEffect>(
                    hasNoModel(),
                    hasEffects(SaveProduct(product))
                )
            )
    }

    @Test
    fun actionButtonClicked_noChange(){
        val model = FoodDetailsModel()

        updateSpec.given(model)
            .whenEvent(ActionButtonClicked)
            .then(
                assertThatNext<FoodDetailsModel, FoodDetailsEffect>(
                    hasNothing()
                )
            )

    }


}