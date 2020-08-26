package com.example.fooddataviewer.di

import android.content.Context
import com.example.fooddataviewer.model.database.ProductDao
import com.example.fooddataviewer.scan.TestFrameProcessorOnSubscribe
import com.example.fooddataviewer.scan.utils.FrameProcessorOnSubscribe
import com.example.fooddataviewer.utils.IdlingResource
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class, ApiModule::class, DatabaseModule::class, TestModule::class])
interface TestComponent : ApplicationComponent{

    fun idlingResource(): IdlingResource

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun productDao(productDao: ProductDao): Builder

        fun build(): TestComponent
    }
}

@Module
object TestModule{
    @Singleton
    @JvmStatic
    @Provides
    fun frameProcessorOnSubscribe(): FrameProcessorOnSubscribe = TestFrameProcessorOnSubscribe()

    @Provides
    @Singleton
    @JvmStatic
    fun idlingResource(): IdlingResource = TestIdlingResource()

}