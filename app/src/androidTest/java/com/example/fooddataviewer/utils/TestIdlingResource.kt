package com.example.fooddataviewer.utils

import androidx.test.espresso.idling.CountingIdlingResource

class TestIdlingResource: IdlingResource {

    val countingIdlingResource = CountingIdlingResource("IdlingResource")

    override fun increment() {
        countingIdlingResource.increment()
    }

    override fun decrement() {
        countingIdlingResource.decrement()
    }
}