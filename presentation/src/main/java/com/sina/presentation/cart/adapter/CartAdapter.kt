package com.sina.presentation.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.core.R
import com.sina.core.databinding.ItemCartBinding
import com.sina.core.model.order.Order.LineItem
import com.sina.core.uicomponents.extentions.formatPrice
import com.sina.core.uicomponents.extentions.loadCoil


class CartAdapter(
    private val itemQuantity: (Int, Int) -> Unit,
    private val itemClick: (Int) -> Unit
) : ListAdapter<LineItem, CartAdapter.ViewHolder>(object : DiffUtil.ItemCallback<LineItem?>() {
    override fun areItemsTheSame(oldItem: LineItem, newItem: LineItem): Boolean =
        oldItem.lineItemId == newItem.lineItemId

    override fun areContentsTheSame(oldItem: LineItem, newItem: LineItem): Boolean =
        oldItem == newItem
}) {
    inner class ViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var currentItem: LineItem
        var quantity = 1

        init {
            binding.root.apply {
                setOnClickListener {
                    getItem(adapterPosition).product_id?.let { id ->
                        itemClick(
                            id
                        )
                    }
                }
            }
            binding.btnIncreaseOrder.setOnClickListener {
                quantity++
                binding.btnDecreaseOrder.setImageResource(R.drawable.ic_minus)
                binding.tvProductQuantity.text = quantity.toString()
                currentItem.lineItemId?.let { id -> itemQuantity(id, quantity) }
            }
            binding.btnDecreaseOrder.setOnClickListener {
                if (quantity > 0) {
                    quantity--
                    binding.btnDecreaseOrder.setImageResource(R.drawable.ic_minus)
                    binding.tvProductQuantity.text = quantity.toString()
                    currentItem.lineItemId?.let { id -> itemQuantity(id, quantity) }
                }
            }
        }

        fun bind(item: LineItem) {
            currentItem = item
            with(binding) {
                btnDecreaseOrder.setImageResource(if (item.quantity == 1) R.drawable.ic_delete else R.drawable.ic_minus)
                tvProductName.text = item.name
                item.quantity?.let { quantity = it }
                tvProductQuantity.text = item.quantity.toString()
                tvProductPrice.text = item.price?.toDouble()?.formatPrice()
                item.image?.src?.let { imgProduct.loadCoil(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
}