package com.example.fooddataviewer.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fooddataviewer.R
import com.example.fooddataviewer.foodlist.FoodListViewModel
import com.example.fooddataviewer.model.ProductService
import com.example.fooddataviewer.scan.ScanViewModel
import com.example.fooddataviewer.utils.ActivityService
import com.example.fooddataviewer.utils.Navigator
import dagger.*
import dagger.multibindings.IntoMap
import kotlinx.android.synthetic.main.activity.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Provider
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class  ViewModelKey(val value: KClass<out ViewModel>)

@MustBeDocumented
@Qualifier //??
@Retention(AnnotationRetention.RUNTIME)
internal annotation class ApiBaseUrl

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])  //todo: tutaj cos nie gra
interface ApplicationComponent{
    fun viewModelFactory(): ViewModelProvider.Factory

    fun activityService(): ActivityService

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun context(context: Context):Builder

        fun build(): ApplicationComponent
    }
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

    @Provides
    @Singleton
    @JvmStatic
    fun apiBaseUrl(context: Context): String = context.getString(R.string.api_base_url)
}

@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(FoodListViewModel::class)
    abstract fun foodListViewModel(viewModel:FoodListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ScanViewModel::class)
    abstract fun scanViewModel(viewModel: ScanViewModel): ViewModel

}

@Module
object ApiModule{

    @Provides
    @Singleton
    @JvmStatic
    fun okHttpClient():OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun retrofit(@ApiBaseUrl apiBaseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun productService(retrofit:Retrofit): ProductService{
        return retrofit.create(ProductService::class.java)
    }
}
