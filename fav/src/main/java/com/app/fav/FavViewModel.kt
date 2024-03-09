package com.app.fav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.capstone.core.domain.usecase.ProductUseCase

class FavViewModel(productUseCase: ProductUseCase): ViewModel() {
    val favoriteProduct = productUseCase.getFavoriteProduct().asLiveData()
}