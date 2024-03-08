package com.app.capstone.detail

import androidx.lifecycle.ViewModel
import com.app.capstone.core.domain.model.MyProduct
import com.app.capstone.core.domain.usecase.ProductUseCase

class DetailViewModel(private val productUseCase: ProductUseCase): ViewModel() {
    fun setFavorite(myProduct: MyProduct, newStatus: Boolean) =
        productUseCase.setFavoriteProduct(myProduct, newStatus)
}