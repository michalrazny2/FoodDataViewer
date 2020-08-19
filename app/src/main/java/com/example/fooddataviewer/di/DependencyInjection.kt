package com.example.fooddataviewer.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fooddataviewer.R
import com.example.fooddataviewer.foodlist.FoodListViewModel
import com.example.fooddataviewer.utils.ActivityService
import com.example.fooddataviewer.utils.Navigator
import dagger.*
import dagger.multibindings.IntoMap
import kotlinx.android.synthetic.main.activity.*
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class  ViewModelKey(val value: KClass<out ViewModel>)

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])  //todo: tutaj cos nie gra
interface ApplicationComponent{
    fun viewModelFactory(): ViewModelProvider.Factory

    fun activityService(): ActivityService
}

@Module
object ApplicationModule{

    @Provides  //TODO: tutaj pod @Provides byl @Singleton, wywalilem i zaczelo dzialac!
    @Singleton
    @JvmStatic
    fun viewModel(providers: MutableMap<Class<out ViewModel>, Provider<ViewModel>>):ViewModelProvider.Factory{
        //the function will give us some factoryrr
        //we can ask for any module and provider will give it to us
        return ViewModelFactory(providers)
    }

    @Provides
    @Singleton
    @JvmStatic
    fun activityService(): ActivityService = ActivityService()

    @Provides
    @Singleton
    @JvmStatic
    fun navigator(activityService: ActivityService): Navigator{
        return Navigator(R.id.navigationHostFragment, activityService)
    }
}

@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(FoodListViewModel::class)
    abstract fun foodListViewModel(viewModel:FoodListViewModel): ViewModel

}