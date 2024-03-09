package com.app.capstone.core.utils

import com.app.capstone.core.data.source.local.entity.ProductEntity
import com.app.capstone.core.data.source.remote.response.ProductsItem
import com.app.capstone.core.domain.model.MyProduct

object DataMapper {
    fun mapResponsesToEntities(input: List<ProductsItem>): List<ProductEntity> {
        val productList = ArrayList<ProductEntity>()
        input.map {
            val products = ProductEntity (
                id = it.id,
                category = it.category,
                description = it.description,
                image = it.image,
                price = it.price,
                title = it.title,
                isFavorite = false
            )
            productList.add(products)
        }
        return productList
    }

    fun mapEntitiesToDomain(input: List<ProductEntity>): List<MyProduct> =
        input.map {
            MyProduct(
                id = it.id,
                category = it.category,
                description = it.description,
                image = it.image,
                price = it.price,
                title = it.title,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: MyProduct) =
        ProductEntity(
        id = input.id,
        category = input.category,
        description = input.description,
        image = input.image,
        price = input.price,
        title = input.title,
        isFavorite = input.isFavorite
    )
}