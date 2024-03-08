package com.app.capstone.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.capstone.core.data.Resource
import com.app.capstone.core.ui.ProductsAdapter
import com.app.capstone.databinding.ActivityMainBinding
import com.app.capstone.fav.FavoriteActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding
    private val productsAdapter = ProductsAdapter()
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(saved: Bundle?) {
        super.onCreate(saved)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.fav.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }

        mainViewModel.product.observe(this){
            if (it != null){
                when (it) {
                    is Resource.Loading -> bind.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        bind.progressBar.visibility = View.GONE
                        productsAdapter.setData(it.data)
                    }

                    is Resource.Error -> {
                        bind.progressBar.visibility = View.GONE
                        bind.viewError.root.visibility = View.VISIBLE
                        bind.viewError.tvError.text =
                            it.message ?: "Ada Yang Salah"
                    }
                }
            }
        }

        bind.rv.let {
            it.layoutManager = LinearLayoutManager(this)
            it.setHasFixedSize(true)
            it.adapter = productsAdapter
        }

    }
}