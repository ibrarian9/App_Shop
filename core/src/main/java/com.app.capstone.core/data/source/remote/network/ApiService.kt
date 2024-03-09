package com.app.capstone.core.data.source.remote.network

import com.app.capstone.core.data.source.remote.response.ProductsItem
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getAllProduct(): List<ProductsItem>
}