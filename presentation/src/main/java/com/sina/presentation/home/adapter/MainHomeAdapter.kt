package com.sina.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sina.core.databinding.ItemProductsBinding
import com.sina.core.databinding.ItemProductsMainBinding
import com.sina.core.model.productlist.Products
import com.sina.core.uicomponents.extentions.loadCoil
import com.sina.presentation.home.HomeViewModel

class MainHomeAdapter(
    private val onClick: (Int) -> Unit,
    private val onMoreClick: (String) -> Unit,
) :
    ListAdapter<HomeViewModel.MainProducts, MainHomeAdapter.ViewHolder>(object :
        DiffUtil.ItemCallback<HomeViewModel.MainProducts>() {
        override fun areItemsTheSame(oldItem: HomeViewModel.MainProducts, newItem: HomeViewModel.MainProducts): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: HomeViewModel.MainProducts, newItem: HomeViewModel.MainProducts): Boolean =
            oldItem == newItem
    }) {
    inner class ViewHolder(private val binding: ItemProductsMainBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var item: HomeViewModel.MainProducts

        init {
            binding.tvSeeMore.setOnClickListener { onMoreClick(item.title) }
        }

        fun bind(mainProducts: HomeViewModel.MainProducts) {
            item = mainProducts
            with(binding) {
                tvProductTitle.text = item.title
                HomeAdapter(onClick).apply {
                    submitList(item.products)
                }.also {
                    rvProductItems.adapter = it
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemProductsMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))




    class HomeAdapter(private val onClick: (Int) -> Unit) :
        ListAdapter<Products, HomeAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Products>() {
            override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean = oldItem == newItem
        }) {
        inner class ViewHolder(private val binding: ItemProductsBinding) : RecyclerView.ViewHolder(binding.root) {
            private lateinit var item: Products

            init {
                binding.root.setOnClickListener {
                    item.id?.let { id -> onClick.invoke(id) }
                }
            }

            fun bind(productsItem: Products) {
                item = productsItem
                with(binding) {
                    tvProductName.text = item.name
                    tvProductPrice.text = item.price
                    item.images?.let {
                        if (it.isNotEmpty()) {
                            imgProduct.loadCoil(
                                it.first().src
                            )
                        }
                    }

//                    item.images?.first()?.let { imgProduct.loadCoil(it.src) }
//                    imgProduct.loadGlide(item.images?.get(0)?.src)
//                    if (adapterPosition == itemCount - 1) onReachedEndOfList.invoke(true) else onReachedEndOfList.invoke(false)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(ItemProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))
    }
}