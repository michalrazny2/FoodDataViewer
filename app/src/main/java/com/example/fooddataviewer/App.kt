package com.example.fooddataviewer

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.fooddataviewer.di.ApplicationComponent
import com.example.fooddataviewer.di.DaggerApplicationComponent
import kotlinx.android.synthetic.*
import kotlin.reflect.KClass

class App : Application() {

    val component by lazy{
        DaggerApplicationComponent
            .builder()
            .build()
    }
}
// Extension function that let us get component everywhere we got Context
val Context.component: ApplicationComponent
get() = (this.applicationContext as App).component

//all fragments have no function getViewModel which allows us to get the right ViewModel for that fragment
fun<T,M,E> Fragment.getViewModel(type: KClass<T>): BaseViewModel<M,E> where T:ViewModel, T: BaseViewModel<M,E>{
    val factory = this.context!!.component.viewModelFactory()
    return ViewModelProviders.of(this,factory)[type.java]
}

fun<T,M,E> FragmentActivity.getViewModel(type: KClass<T>): BaseViewModel<M,E> where T:ViewModel, T: BaseViewModel<M,E>{
    val factory = this.applicationContext!!.component.viewModelFactory()
    return ViewModelProviders.of(this,factory)[type.java]
}