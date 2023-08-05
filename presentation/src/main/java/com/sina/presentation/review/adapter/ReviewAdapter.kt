package com.sina.presentation.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.core.R
import com.sina.core.databinding.ItemReviewBinding
import com.sina.core.model.review.Review
import com.sina.core.uicomponents.extentions.fromURI

class ReviewAdapter(
    private val onclick: (Int) -> Unit,
    private val onDeleteClick: (Int, Int) -> Unit,
    private val onEditClick: (Int) -> Unit
) :
    ListAdapter<Review, ReviewAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean = oldItem == newItem
    }) {
    inner class ViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Review) {
            with(binding) {
                binding.root.setOnClickListener {
                    item.id?.let { id -> onclick(id) }
                }
                btnDeleteReview.setOnClickListener {
                    item.id?.let { id -> onDeleteClick(id, adapterPosition) }
                }
                btUpdateReview.setOnClickListener {
                    item.id?.let { id -> onEditClick(id) }
                }
                if (item.rating > 3) tvReviewRateText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    R.drawable.ic_thumb_up,
                    0,
                    0,
                    0
                ) else tvReviewRateText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    R.drawable.ic_thumb_down,
                    0,
                    0,
                    0
                )
                tvReviewTitle.text = item.reviewer
                tvReviewPoint.text = item.rating.toString()
                tvReviewDescription.text = item.review.fromURI()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

}
