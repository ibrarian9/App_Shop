package com.app.capstone.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
        bind = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val detailProduct = intent.getParcelableExtra<MyProduct>(EXTRA)
        setDataProduct(detailProduct)
    }

    private fun setDataProduct(detailProduct: MyProduct?) {
        detailProduct?.let {product ->
            Picasso.get().load(product.image).into(bind.foto)
            bind.judul.text = product.title
            bind.desc.text = product.description

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

    companion object {
        const val EXTRA = "extra"
    }
}