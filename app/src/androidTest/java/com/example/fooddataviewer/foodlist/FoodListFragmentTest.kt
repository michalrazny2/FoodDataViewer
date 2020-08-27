package com.example.fooddataviewer.foodlist

import android.content.res.Resources
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.fooddataviewer.AndroidTestApplication
import com.example.fooddataviewer.R
import com.example.fooddataviewer.dto.NutrimentsDto
import com.example.fooddataviewer.dto.ProductDto
import com.example.fooddataviewer.fooddetails.FoodDetailsFragment
import com.example.fooddataviewer.utils.withRecyclerView
import io.fotoapparat.selector.macro
import org.junit.Test

class FoodListFragmentTest {

    private val id = "1234"
    private val name = "name"
    private val brands = "brands"
    private val imageUrl = "imageUrl"
    private val ingredients = "ingredients"
    private val energy = 10
    private val salt = 11.0
    private val carbs = 12.0
    private val fiber = 13.0
    private val sugars = 14.0
    private val proteins = 15.0
    private val fat = 10.0
    private val productDto = ProductDto(
        id = id,
        product_name = name,
        brands = brands,
        image_url = imageUrl,
        ingredients,
        NutrimentsDto(energy, salt, carbs, fiber, sugars, proteins, fat)
    )

    @Test
    fun views(){
        var resources: Resources? = null
        val scenario =
            launchFragmentInContainer<FoodListFragment>(
                themeResId = R.style.Theme_MaterialComponents_Light_NoActionBar
            )

        scenario.onFragment { fragment ->
            (fragment.activity!!.applicationContext as AndroidTestApplication).productDtoSubject.onNext(
            listOf(
                productDto
            ))
        }

        // assertions section :
        onView(withRecyclerView(R.id.recycler_view) //we use recyclerViewMatcher to check if
            .atPositionOnView(0, R.id.productNameView)) // the recyclerview works correctly
            .check(matches(withText(name)))
        // todo: in fact here withIds should be changed on withRecyclerViews etc.
        onView(withId(R.id.productView)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.productNameView)).check(matches(withText("Coke Classic")))
        onView(withId(R.id.brandNameView)).check(matches(withText("Coca-cola")))
        onView(withId(R.id.energyValueView)).check(matches(withText(resources!!.getString(
            R.string.food_list_energy_value, energy   // TODO: some mistakes in the string names
        ))))
        onView(withId(R.id.carbsValueView)).check(matches(withText(resources!!.getString(
            R.string.food_list_macro_value, fat
        ))))
        onView(withId(R.id.fatValueView)).check(matches(withText(resources!!.getString(
            R.string.food_list_energy_value, energy
        ))))
    }


    @Test
    fun views2(){
        val scenario =
            launchFragmentInContainer<FoodListFragment>(
                themeResId = R.style.Theme_MaterialComponents_Light_NoActionBar
            )
        val otherName = "name2"
        scenario.onFragment { fragment ->
            (fragment.activity!!.applicationContext as AndroidTestApplication).productDtoSubject.onNext(
                listOf(
                    productDto,
                    productDto.copy(product_name = otherName)
                )
            )
        }
        onView(withRecyclerView(R.id.recycler_view).atPositionOnView(1, R.id.productNameView))
            .check(matches(withText(otherName)))
    }

}