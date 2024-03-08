package com.app.capstone.di

import com.app.capstone.core.domain.usecase.ProductInteractor
import com.app.capstone.core.domain.usecase.ProductUseCase
import com.app.capstone.detail.DetailViewModel
import com.app.capstone.fav.FavViewModel
import com.app.capstone.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<ProductUseCase> { ProductInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { FavViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}
