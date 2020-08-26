package com.example.fooddataviewer.utils

import androidx.test.espresso.idling.CountingIdlingResource

class TestIdlingResource: IdlingResource {

    //Basically if the countingIdlingResource is more then 0 during Espresso check
    // test will wait with its assertions <?>
    val countingIdlingResource = CountingIdlingResource("IdlingResource")

    override fun increment() {
        countingIdlingResource.increment()
    }

    override fun decrement() {
        countingIdlingResource.decrement()
    }
}