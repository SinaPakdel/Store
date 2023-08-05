package com.sina.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.core.databinding.ItemSearchProductsBinding
import com.sina.core.model.productlist.Products
import com.sina.core.uicomponents.extentions.loadGlide

class SearchAdapter (private val onClick: (Int) -> Unit) :
    ListAdapter<Products, SearchAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Products>() {
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean = oldItem == newItem
    }) {
    inner class ViewHolder(private val binding: ItemSearchProductsBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                getItem(adapterPosition).id?.let { id -> onClick(id) }
            }
        }

        fun bind(products: Products) {
            with(binding) {
                tvProductName.text = products.name
                tvProductPrice.text = products.price
                imgProduct.loadGlide(products.images?.get(0)?.src)
                // TODO: create list of images for item of products
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemSearchProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))
}