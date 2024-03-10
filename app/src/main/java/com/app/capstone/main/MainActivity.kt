package com.app.capstone.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.capstone.R
import com.app.capstone.core.data.Resource
import com.app.capstone.core.ui.ProductsAdapter
import com.app.capstone.databinding.ActivityMainBinding
import com.app.capstone.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding
    private lateinit var broadcastReceiver: BroadcastReceiver
    private val productsAdapter = ProductsAdapter()
    private val mainViewModel: MainViewModel by viewModel()

    override fun onStart() {
        super.onStart()
        broadcastReceiver()
    }

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

    private fun broadcastReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    Intent.ACTION_POWER_CONNECTED -> {
                        bind.power.text = getString(R.string.power_connected)
                    }
                    Intent.ACTION_POWER_DISCONNECTED -> {
                        bind.power.text = getString(R.string.power_disconnected)
                    }
                }
            }

        }

        val intentFilter = IntentFilter()
        intentFilter.apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }

        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }
}