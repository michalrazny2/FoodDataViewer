package com.example.fooddataviewer.fooddetails

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.fooddataviewer.R
import com.example.fooddataviewer.getViewModel
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.food_details_fragment.*

class FoodDetailsFragment : Fragment(R.layout.food_details_fragment) {

    private val args: FoodDetailsFragmentArgs by navArgs() //TODO: o co chodzi z tymi argsami...
    private lateinit var disposable: Disposable

    override fun onStart() {
        super.onStart()

        disposable = actionButton.clicks().map{ActionButtonClicked}
            .compose(getViewModel(FoodDetailsViewModel::class).init(Initial(args.barcode))) //we passed barcode to args in ScanViewModel before
            .subscribe()
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }
}