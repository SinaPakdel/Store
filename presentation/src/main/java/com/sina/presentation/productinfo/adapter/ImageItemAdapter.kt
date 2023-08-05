package com.sina.presentation.productinfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.core.databinding.ItemImageProductBinding
import com.sina.core.model.productinfo.ProductInfo
import com.sina.core.uicomponents.extentions.loadGlide

class ImageDetailsAdapter : ListAdapter<ProductInfo.Image, ImageDetailsAdapter.ViewHolder>(object : DiffUtil.ItemCallback<ProductInfo.Image?>() {
    override fun areItemsTheSame(oldItem: ProductInfo.Image, newItem: ProductInfo.Image): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: ProductInfo.Image, newItem: ProductInfo.Image): Boolean = oldItem == newItem
}) {
    inner class ViewHolder(private val binding: ItemImageProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductInfo.Image) {
            with(binding) {
                imgItemProduct.loadGlide(item.src)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemImageProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

}
