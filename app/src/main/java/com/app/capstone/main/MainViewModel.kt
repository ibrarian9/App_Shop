package com.app.capstone.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.app.capstone.core.domain.usecase.ProductUseCase

class MainViewModel(productUseCase: ProductUseCase) : ViewModel() {
    val product = productUseCase.getAllProduct().asLiveData()
}