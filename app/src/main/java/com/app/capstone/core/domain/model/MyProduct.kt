package com.app.capstone.core.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyProduct(

    val id: Int,
    val category: String,
    val description: String,
    val image: String,
    val price: String,
    val rating: Rating?,
    val title: String,
    val isFavorite: Boolean

): Parcelable

@Parcelize
data class Rating(

    @field:SerializedName("rate")
    val rate: Double,

    @field:SerializedName("count")
    val count: Int

): Parcelable