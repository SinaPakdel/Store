package com.sina.presentation.productlsit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.core.databinding.ItemProductsBinding
import com.sina.core.model.productlist.Products
import com.sina.core.uicomponents.extentions.loadCoil

class ProductsAdapter(private val onClick: (Int) -> Unit) :
    ListAdapter<Products, ProductsAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Products>() {
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean = oldItem == newItem
    }) {
    inner class ViewHolder(private val binding: ItemProductsBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                getItem(adapterPosition).id?.let { id -> onClick(id) }
            }
        }

        fun bind(products: Products) {
            with(binding) {
                tvProductName.text = products.name
                tvProductPrice.text = products.price
                products.images?.let {
                    if (it.isNotEmpty()) {
                        imgProduct.loadCoil(
                            it.first().src
                        )
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))
}