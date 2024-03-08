package com.app.capstone.fav

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.capstone.R
import com.app.capstone.core.ui.ProductsAdapter
import com.app.capstone.databinding.ActivityFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var bind: ActivityFavoriteBinding
    private val productsAdapter = ProductsAdapter()
    private val favViewModel: FavViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityFavoriteBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(bind.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        favViewModel.favoriteProduct.observe(this){
            println(it)
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