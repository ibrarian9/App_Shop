package com.app.fav.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.capstone.core.ui.ProductsAdapter
import com.app.capstone.detail.DetailActivity
import com.app.fav.databinding.ActivityFavoriteBinding
import com.app.fav.di.favModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private lateinit var bind: ActivityFavoriteBinding
    private val productsAdapter = ProductsAdapter()
    private val favViewModel: FavViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(bind.root)

        loadKoinModules(favModule)

        productsAdapter.onItemClick = {
            val i = Intent(this, DetailActivity::class.java)
            i.putExtra(DetailActivity.EXTRA, it)
            startActivity(i)
        }

        favViewModel.favoriteProduct.observe(this){
            productsAdapter.submitList(it)
        }

        bind.rv.let {
            it.layoutManager = LinearLayoutManager(this)
            it.setHasFixedSize(true)
            it.adapter = productsAdapter
        }
    }
}