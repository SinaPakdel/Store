package com.sina.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.core.databinding.ItemSliderHomeBinding
import com.sina.core.model.productlist.Products
import com.sina.core.uicomponents.extentions.loadGlide

class HomeSliderAdapter :
    ListAdapter<Products.Image, HomeSliderAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Products.Image?>() {
        override fun areItemsTheSame(oldItem: Products.Image, newItem: Products.Image): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Products.Image, newItem: Products.Image): Boolean =
            oldItem == newItem
    }) {
    inner class ViewHolder(private val binding: ItemSliderHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Products.Image) {
            binding.imgSliderHome.loadGlide(item.src)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemSliderHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))
}
