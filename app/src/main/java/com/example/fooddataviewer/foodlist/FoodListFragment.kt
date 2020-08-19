package com.example.fooddataviewer.foodlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fooddataviewer.App
import com.example.fooddataviewer.R
import com.example.fooddataviewer.getViewModel
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.food_list_fragment.*

class FoodListFragment: Fragment(R.layout.food_list_fragment) {

    override fun onStart() {
        super.onStart()
        addButon.clicks().map{AddButtonClicked}
            .compose(getViewModel(FoodListViewModel::class))
            .subscribe()



    }

}