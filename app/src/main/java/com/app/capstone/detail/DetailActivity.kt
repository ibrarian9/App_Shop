package com.app.capstone.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.capstone.R
import com.app.capstone.core.domain.model.MyProduct
import com.app.capstone.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var bind: ActivityDetailBinding
    private val detailProductViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bind = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val detailProduct = intent.getParcelableExtra<MyProduct>("ExtraData")
        setDataProduct(detailProduct)
    }

    private fun setDataProduct(detailProduct: MyProduct?) {
        detailProduct?.let {
            Picasso.get().load(it.image).into(bind.foto)
            bind.judul.text = it.title
            bind.desc.text = it.description

            var statusFav = detailProduct.isFavorite
            setStatus(statusFav)
            bind.fav.setOnClickListener {
                statusFav = !statusFav
                detailProductViewModel.setFavorite(detailProduct, statusFav)
                setStatus(statusFav)
            }

        }
    }

    private fun setStatus(statusFav: Boolean) {
        if (statusFav){
            bind.fav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_star_24))
        } else {
            bind.fav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_star_outline_24))
        }
    }
}