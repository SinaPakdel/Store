package com.sina.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.core.databinding.ItemSearchOrderBinding
import com.sina.core.model.search.SearchOrderItem

class SearchOrderByAdapter(private val onClick: (id: SearchOrderItem) -> Unit) :
    ListAdapter<SearchOrderItem, SearchOrderByAdapter.ViewHolder>(object : DiffUtil.ItemCallback<SearchOrderItem?>() {
        override fun areItemsTheSame(oldItem: SearchOrderItem, newItem: SearchOrderItem): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: SearchOrderItem, newItem: SearchOrderItem): Boolean = oldItem == newItem

    }) {
    inner class ViewHolder(private val binding: ItemSearchOrderBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { onClick(getItem(adapterPosition)) }
        }

        fun bind(item: SearchOrderItem) {
            with(binding) {
                tvSearchOrder.text = item.orderTitle
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemSearchOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))
}