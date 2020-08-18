package com.example.fooddataviewer.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fooddataviewer.foodlist.FoodListViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class  ViewModelKey(val value: KClass<out ViewModel>)

@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent{
    fun viewModelFactory(): ViewModelProvider.Factory
}

@Module
object ApplicationModule{

    @Provides
    @Singleton
    @JvmStatic
    fun viewModel(providers: MutableMap<Class<out ViewModel>, Provider<ViewModel>>):ViewModelProvider.Factory{
        //the function will give us some factoryrr
        //we can ask for any module and provider will give it to us
        return ViewModelFactory(providers)
    }
}

@Module
abstract class ViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(FoodListViewModel::class)
    abstract fun foodListViewModel(viewModel:FoodListViewModel): FoodListViewModel

}