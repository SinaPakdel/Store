package com.sina.presentation.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.core.databinding.ItemCategoryBinding
import com.sina.core.model.category.Category
import com.sina.core.uicomponents.extentions.loadGlide

class CategoryAdapter(
    private val onClick: (Int) -> Unit,
) : ListAdapter<Category, CategoryAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean = oldItem.id == newItem.id
}) {
    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.apply {
                setOnClickListener { getItem(adapterPosition).id?.let { id -> onClick(id) } }
            }
        }

        fun bind(categoryDTOItem: Category) {
            binding.tvCategoryName.text = categoryDTOItem.name
            categoryDTOItem.image?.src?.let { binding.imvCategory.loadGlide(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))
}