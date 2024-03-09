package com.app.capstone.core.domain.usecase

import com.app.capstone.core.domain.model.MyProduct
import com.app.capstone.core.domain.repository.IProductRepository

class ProductInteractor(private val productRepository: IProductRepository): ProductUseCase {

    override fun getAllProduct() = productRepository.getAllProduct()

    override fun getFavoriteProduct() = productRepository.getFavoriteProduct()

    override fun setFavoriteProduct(product: MyProduct, state: Boolean)
    = productRepository.setFavoriteProduct(product, state)
}