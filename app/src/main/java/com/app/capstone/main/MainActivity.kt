package com.app.capstone.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.capstone.core.data.Resource
import com.app.capstone.core.ui.ProductsAdapter
import com.app.capstone.databinding.ActivityMainBinding
import com.app.capstone.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding
    private val productsAdapter = ProductsAdapter()
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(saved: Bundle?) {
        super.onCreate(saved)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        productsAdapter.onItemClick = {
            val i = Intent(this, DetailActivity::class.java)
            i.putExtra(DetailActivity.EXTRA, it)
            startActivity(i)
        }

        bind.fav.setOnClickListener {
            try {
                startActivity(Intent(this, Class.forName("com.app.fav.favorite.FavoriteActivity")))
            } catch (e: Exception) {
                Toast.makeText(this, "Module tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }

        mainViewModel.product.observe(this){
            if (it != null){
                when (it) {
                    is Resource.Loading -> bind.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        bind.progressBar.visibility = View.GONE
                        productsAdapter.submitList(it.data)
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