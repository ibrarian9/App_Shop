package com.app.capstone.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "price")
    val price: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false

)

