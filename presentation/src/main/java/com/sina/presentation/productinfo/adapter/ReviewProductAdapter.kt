package com.sina.presentation.productinfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.core.R
import com.sina.core.databinding.ItemReviewProductBinding
import com.sina.core.network.model.reviewdto.ReviewDto
import com.sina.core.uicomponents.extentions.fromURI

class ReviewProductAdapter(private val onclick: (Int) -> Unit) :
    ListAdapter<ReviewDto, ReviewProductAdapter.ViewHolder>(object : DiffUtil.ItemCallback<ReviewDto>() {
        override fun areItemsTheSame(oldItem: ReviewDto, newItem: ReviewDto): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ReviewDto, newItem: ReviewDto): Boolean = oldItem == newItem
    }) {
    inner class ViewHolder(private val binding: ItemReviewProductBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(item: ReviewDto) {
            binding.root.setOnClickListener { }
            with(binding) {
                tvReviewTitle.text = item.reviewer
                tvReviewPoint.text = item.rating.toString()
                tvReviewDescription.text = item.review.fromURI()

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
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemReviewProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

}
