package com.app.capstone.core.domain.usecase

import com.app.capstone.core.data.Resource
import com.app.capstone.core.domain.model.MyProduct
import kotlinx.coroutines.flow.Flow

interface ProductUseCase {
    fun getAllProduct(): Flow<Resource<List<MyProduct>>>
    fun getFavoriteProduct(): Flow<List<MyProduct>>
    fun setFavoriteProduct(product: MyProduct, state: Boolean)
}