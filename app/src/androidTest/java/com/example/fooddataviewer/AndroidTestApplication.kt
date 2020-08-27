package com.example.fooddataviewer

import com.example.fooddataviewer.di.DaggerTestComponent

class AndroidTestApplication: App(){

    override val component by lazy{
        DaggerTestComponent.builder()
            .context(this)
            .build()
    }
}