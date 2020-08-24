package com.example.fooddataviewer.ScanTests.foodlist

import com.example.fooddataviewer.foodlist.*
import com.example.fooddataviewer.model.Product
import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Before
import org.junit.Test

class FoodListUpdateTest {

    private lateinit var updateSpec: UpdateSpec<FoodListModel, FoodListEvent, FoodListEffect>

    @Before //setup before tests
    fun before(){
        updateSpec = UpdateSpec(::foodListUpdate)
    }

    @Test
    fun initial_observerProductDispatched(){
        val model = FoodListModel()

        updateSpec.given(model)
            .whenEvent(Initial)
            .then(
                assertThatNext<FoodListModel, FoodListEffect>(
                    hasNoModel(),
                    hasEffects(ObserveProducts)
                )
            )
    }

    @Test
    fun addButtonClicked_NavigateToScannerDispatched(){
        val model = FoodListModel()

        updateSpec.given(model)
            .whenEvent(AddButtonClicked)
            .then(
                assertThatNext<FoodListModel, FoodListEffect>(
                    hasNoModel(),
                    hasEffects(ObserveProducts)
                )
            )
    }

    @Test
    fun productLoaded_nextModel(){
        val product = Product(
            id = "someId",
            saved = false,
            name = "product",
            brands = "brand",
            imageUrl = "www.something.pl",
            ingridients = "sugar",
            nutriments = null
        )
        val products = listOf(product, product, product)
        val model = FoodListModel(products = products)

        updateSpec.given(model)
            .whenEvent(ProductsLoaded(products))
            .then(
                assertThatNext<FoodListModel, FoodListEffect>(
                    hasModel(model.copy(products)),
                    hasEffects(ObserveProducts)
                )
            )
    }

    @Test
    fun productClicked_navigateToFoodDetailsDispatched(){
        val model = FoodListModel()
        val id = "Somebarcode"

        updateSpec.given(model)
            .whenEvent(ProductClicked(id))
            .then(
                assertThatNext<FoodListModel, FoodListEffect>(
                    hasNoModel(),
                    hasEffects(NavigateToFoodDetails(id))
                )
            )
    }



}