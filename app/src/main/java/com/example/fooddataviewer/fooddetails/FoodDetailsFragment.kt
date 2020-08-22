package com.example.fooddataviewer.fooddetails

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.fooddataviewer.R
import com.example.fooddataviewer.getViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

class FoodDetailsFragment : Fragment(R.layout.food_details_fragment) {

    private val args: FoodDetailsFragmentArgs by navArgs()
    private lateinit var disposable: Disposable

    override fun onStart() {
        super.onStart()

        disposable = Observable.empty<FoodDetailsEvent>()
            .compose(getViewModel(FoodDetailsViewModel::class))
            .subscribe()
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }
}