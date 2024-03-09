package com.app.capstone.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.capstone.core.domain.model.MyProduct
import com.app.core.databinding.ListItemBinding
import com.squareup.picasso.Picasso

class ProductsAdapter : ListAdapter<MyProduct, ProductsAdapter.ListViewHolder>(DiffCallback) {

    var onItemClick: ((MyProduct) -> Unit)? = null

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<MyProduct>() {
            override fun areItemsTheSame(oldItem: MyProduct, newItem: MyProduct): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MyProduct, newItem: MyProduct): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ListViewHolder(private val bind: ListItemBinding): RecyclerView.ViewHolder(bind.root) {
        fun binding(dataList: MyProduct) {
            with(bind) {
                Picasso.get().load(dataList.image).fit().into(image)
                laptop.text = dataList.title
                desc.text = dataList.description
                harga.text = dataList.price
            }
        }

        init {
            bind.root.setOnClickListener {
                onItemClick?.invoke(getItem(bindingAdapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val bind = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val dataList = getItem(position)
        holder.binding(dataList)
    }
}
