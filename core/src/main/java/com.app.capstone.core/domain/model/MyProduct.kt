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
    val title: String,
    val isFavorite: Boolean

): Parcelable