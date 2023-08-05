package com.sina.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.core.databinding.ItemSearchFilterBinding
import com.sina.core.model.search.SearchFilterItem

class SearchFilterAdapter(private val onClick: (id: Int) -> Unit) :
    ListAdapter<SearchFilterItem, SearchFilterAdapter.ViewHolder>(object : DiffUtil.ItemCallback<SearchFilterItem?>() {
        override fun areItemsTheSame(oldItem: SearchFilterItem, newItem: SearchFilterItem): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: SearchFilterItem, newItem: SearchFilterItem): Boolean = oldItem == newItem

    }) {
    inner class ViewHolder(private val binding: ItemSearchFilterBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { onClick(getItem(adapterPosition).id) }
        }

        fun bind(item: SearchFilterItem) {
            with(binding) {
                tvSearchFilter.text = item.filterTitle
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemSearchFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))
}