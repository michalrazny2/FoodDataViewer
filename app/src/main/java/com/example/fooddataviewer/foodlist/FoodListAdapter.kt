package com.example.fooddataviewer.foodlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddataviewer.R
import com.example.fooddataviewer.model.Product
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.product_layout_small.*

class FoodListAdapter: ListAdapter<Product,FoodListProductViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListProductViewHolder {
        return FoodListProductViewHolder(LayoutInflater.from(parent.context).inflate(
            viewType,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: FoodListProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.food_list_product_item
    }

}

// we need diff util callback because FoodListAdapter extends
// ListAdapter and needs DiffUtilCallback in its contructor
private class DiffUtilCallback: DiffUtil.ItemCallback<Product>(){

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem == newItem

}

class FoodListProductViewHolder(
    override val containerView: View
): RecyclerView.ViewHolder(containerView), LayoutContainer{

    fun bind(product: Product){
        val context = containerView.context

        productNameView.text = product.name
        brandNameView.text = product.brands
//                    energyValueView.text = context.getString(
//                        R.string.scan_energy_value,
//                        model.processBarcodeResult.product.nutriments?.energy
//                    )
        carbsValueView.text = context.getString(
            R.string.scan_macro_value,
            product.nutriments?.carbohydrates
        )
        fatValueView.text = context.getString(
            R.string.scan_macro_value,
            product.nutriments?.fat
        )
        proteinValueView.text = context.getString(
            R.string.scan_macro_value,
            product.nutriments?.proteins
        )

        Glide.with(context)
            .load(product.imageUrl)
            .fitCenter()
            .into(productImageView)
    }

}