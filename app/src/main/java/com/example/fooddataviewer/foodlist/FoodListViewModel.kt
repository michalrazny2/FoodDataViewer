package com.example.fooddataviewer.foodlist

import com.example.fooddataviewer.MobiusVM
import com.spotify.mobius.Next
import com.spotify.mobius.Update
import com.spotify.mobius.rx2.RxMobius
import javax.inject.Inject

fun foodListUpdate(
    model: FoodListModel,
    event: FoodListEvent
): Next<FoodListModel, FoodListEfect> {
    return Next.next(FoodListModel)
}

class FoodListViewModel @Inject constructor(): MobiusVM<FoodListModel,FoodListEvent,FoodListEfect>(
    "FoodListViewModel",
    Update(::foodListUpdate),
    FoodListModel,
    RxMobius.subtypeEffectHandler<FoodListEfect, FoodListEvent>()
        .build()
    ) {
}