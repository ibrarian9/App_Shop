package com.app.capstone.core.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.capstone.core.domain.model.MyProduct
import com.app.capstone.databinding.ListItemBinding
import com.app.capstone.detail.DetailActivity
import com.squareup.picasso.Picasso

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ListViewHolder>() {

    private var listData = ArrayList<MyProduct>()

    fun setData(newData: List<MyProduct>?) {
        if (newData == null) return
        listData.clear()
        listData.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val bind: ListItemBinding): RecyclerView.ViewHolder(bind.root) {
        fun binding(dataList: MyProduct) {
           with(bind) {
               Picasso.get().load(dataList.image).fit().into(image)
               laptop.text = dataList.title
               desc.text = dataList.description
               harga.text = dataList.price

               itemView.setOnClickListener {
                   val i = Intent(it.context, DetailActivity::class.java)
                   i.putExtra("ExtraData", dataList)
                   it.context.startActivity(i)
               }
           }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val bind = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(bind)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val dataList = listData[position]
        holder.binding(dataList)
    }
}