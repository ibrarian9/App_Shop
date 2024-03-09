package com.app.fav.di

import com.app.fav.favorite.FavViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favModule = module {
    viewModel { FavViewModel(get()) }
}