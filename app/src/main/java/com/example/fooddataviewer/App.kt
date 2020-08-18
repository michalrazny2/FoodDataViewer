package com.example.fooddataviewer

import android.app.Application
import com.example.fooddataviewer.di.DaggerApplicationComponent
import kotlinx.android.synthetic.*

class App : Application() {

    val component by lazy{
        DaggerApplicationComponent.builder().
                build()
    }



}