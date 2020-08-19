package com.example.fooddataviewer.foodlist

import com.example.fooddataviewer.MobiusVM
import com.spotify.mobius.Next
import com.spotify.mobius.Update
import com.spotify.mobius.rx2.RxMobius
import javax.inject.Inject

fun foodListUpdate(
    model: FoodListModel,
    event: FoodListEvent
): Next<FoodListModel, FoodListEffect> {
    return when(event){
        AddButtonClicked -> Next.dispatch(setOf(NavigateToScanner))

    }
}

class FoodListViewModel @Inject constructor(
    navigator: com.example.fooddataviewer.utils.Navigator
):
    MobiusVM<FoodListModel,FoodListEvent,FoodListEffect>(
    "FoodListViewModel",
    Update(::foodListUpdate),
    FoodListModel,
    RxMobius.subtypeEffectHandler<FoodListEffect, FoodListEvent>()
        .addAction(NavigateToScanner::class.java){
            navigator.to(FoodListFragmentDirections.scan())
        }
        .build()
    )