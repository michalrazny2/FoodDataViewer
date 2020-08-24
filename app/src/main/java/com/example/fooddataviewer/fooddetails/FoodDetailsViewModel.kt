package com.example.fooddataviewer.fooddetails

import android.app.Notification
import androidx.lifecycle.ViewModel
import androidx.room.Delete
import com.example.fooddataviewer.BaseViewModel
import com.example.fooddataviewer.MobiusVM
import com.example.fooddataviewer.model.ProductRepository
import com.spotify.mobius.Next
import com.spotify.mobius.Next.*
import com.spotify.mobius.Update
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

fun foodDetailsUpdate(
    model: FoodDetailsModel,
    event: FoodDetailsEvent
): Next<FoodDetailsModel, FoodDetailsEffect>{
    return when(event){
        is Initial -> next(
            model.copy(activity = true),
            setOf(LoadProduct(event.barcode))
        )
        is ActionButtonClicked -> if(model.product!=null){
            if(model.product.saved){
                next<FoodDetailsModel, FoodDetailsEffect>( // todo not quite get why next, why dispatch
                    model.copy(product = model.product.copy(saved = !model.product.saved)),
                    setOf(DeleteProduct(model.product.id))
                )
            }else{
                next<FoodDetailsModel, FoodDetailsEffect>(
                    model.copy(product = model.product.copy(saved = !model.product.saved)),
                    setOf(SaveProduct(model.product)) //todo to nwm czy napewno
                )
            }
        }else{
            noChange()
        }
        is ProductLoaded -> next(model.copy(activity=false, product = event.product))
        is ErrorLoadingProduct -> next(model.copy(activity=false, error = true))
    }
}

class FoodDetailsViewModel @Inject constructor(
    productRepository: ProductRepository
): MobiusVM<FoodDetailsModel,FoodDetailsEvent,FoodDetailsEffect> (
    "FoodDetailsViewModel",
    Update(::foodDetailsUpdate),
    FoodDetailsModel(),
    RxMobius.subtypeEffectHandler<FoodDetailsEffect, FoodDetailsEvent>()
        .addTransformer(LoadProduct::class.java) { upstream ->
            upstream.switchMap { effect ->
                productRepository.loadProduct(barcode = effect.barcode)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toObservable()
                    .map { product -> ProductLoaded(product) as FoodDetailsEvent }
                    .onErrorReturn { ErrorLoadingProduct }
            }   //action handler for Initial
        }
        .addTransformer(SaveProduct::class.java){
            upstream -> upstream.switchMap { effect ->
            productRepository.saveProduct(effect.product)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toObservable<FoodDetailsEvent>()
        }
        }
        .addTransformer(DeleteProduct::class.java){
            upstream -> upstream.switchMap { effect ->
            productRepository.deleteProduct(effect.barcode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable<FoodDetailsEvent>()
        }
        }
        .build()
)