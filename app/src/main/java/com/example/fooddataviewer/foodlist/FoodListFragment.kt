package com.example.fooddataviewer.foodlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddataviewer.App
import com.example.fooddataviewer.R
import com.example.fooddataviewer.getViewModel
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.food_list_fragment.*

class FoodListFragment: Fragment(R.layout.food_list_fragment) {

    lateinit var disposable: Disposable

    override fun onStart() {
        super.onStart()

        recycler_view.layoutManager = LinearLayoutManager(context)
        val adapter = FoodListAdapter()
        recycler_view.adapter = adapter

        disposable = Observable.mergeArray(
            addButon.clicks().map{AddButtonClicked},
            adapter.productClicks.map{ ProductClicked(it.id)})
                .compose(getViewModel(FoodListViewModel::class).init(Initial))
            .subscribe{ model ->
                adapter.submitList(model.products)
            }
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

}