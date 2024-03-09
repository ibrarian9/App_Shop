package com.app.capstone.core.data

import com.app.capstone.core.data.source.local.LocalDataSource
import com.app.capstone.core.data.source.remote.RemoteDataSource
import com.app.capstone.core.data.source.remote.network.ApiResponse
import com.app.capstone.core.data.source.remote.response.ProductsItem
import com.app.capstone.core.domain.model.MyProduct
import com.app.capstone.core.domain.repository.IProductRepository
import com.app.capstone.core.utils.DataMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ProductRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): IProductRepository {
    override fun getAllProduct(): Flow<Resource<List<MyProduct>>> =
        object : NetworkBoundRes<List<MyProduct>, List<ProductsItem>>() {
            override fun loadFromDB(): Flow<List<MyProduct>> {
                return localDataSource.getAllProduct().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<MyProduct>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<ProductsItem>>> =
                remoteDataSource.getAllProduct()

            override suspend fun saveCallResult(data: List<ProductsItem>) {
                localDataSource.insertProduct(DataMapper.mapResponsesToEntities(data))
            }
        }.asFlow()

    override fun getFavoriteProduct(): Flow<List<MyProduct>> {
        return localDataSource.getFavoriteProduct().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteProduct(product: MyProduct, state: Boolean) {
        val productEntity = DataMapper.mapDomainToEntity(product)
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.setFavoriteProduct(productEntity, state)
        }
    }
}