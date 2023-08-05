package com.sina.presentation.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.core.databinding.ItemAddressBinding
import com.sina.core.model.customer.Customer

class AddressAdapter(private val onClick: (Int) -> Unit) :
    ListAdapter<Customer, AddressAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Customer>() {
        override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean =
            oldItem == newItem
    }) {
    inner class ViewHolder(private val binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                getItem(adapterPosition).id?.let { id -> onClick(id) }
            }
        }

        fun bind(customer: Customer) {
            with(binding) {
                tvAddressCity.text = customer.shipping.address_1
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
}