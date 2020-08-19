package com.example.fooddataviewer.foodlist

import androidx.navigation.Navigator
import com.example.fooddataviewer.MobiusVM
import com.spotify.mobius.Next
import com.spotify.mobius.Update
import com.spotify.mobius.rx2.RxMobius
import javax.inject.Inject

fun foodListUpdate(
    model: FoodListModel,
    event: FoodListEvent
): Next<FoodListModel, FoodListEfect> {
    return when(event){
        AddButtonClicked -> Next.dispatch(setOf(NavigateToScanner))

    }
}

class FoodListViewModel @Inject constructor(navigator: com.example.fooddataviewer.utils.Navigator): MobiusVM<FoodListModel,FoodListEvent,FoodListEfect>(
    "FoodListViewModel",
    Update(::foodListUpdate),
    FoodListModel,
    RxMobius.subtypeEffectHandler<FoodListEfect, FoodListEvent>()
        .addAction(NavigateToScanner::class.java){
            navigator.to(FoodListFragmentDirections.scan())
        }
        .build()
    )