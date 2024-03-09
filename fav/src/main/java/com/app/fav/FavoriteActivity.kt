package com.app.fav

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.capstone.R
import com.app.capstone.core.ui.ProductsAdapter
import com.app.capstone.detail.DetailActivity
import com.app.fav.databinding.ActivityFavoriteBinding
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
            if (it != null){
                productsAdapter.setData(it)
            }
        }

        bind.rv.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = productsAdapter
        }

    }
}