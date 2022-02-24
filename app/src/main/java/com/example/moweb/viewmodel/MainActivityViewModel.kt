package com.example.moweb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moweb.model.CategoryResponse
import com.example.moweb.model.ProductitemResponse
import com.example.moweb.repository.MainActivityRepository

class MainActivityViewModel : ViewModel() {

    var servicesProductData: MutableLiveData<CategoryResponse>? = null

    init {
        servicesProductData = MainActivityRepository.serviceProduct
    }


    fun getproduct() {
        MainActivityRepository.getProductApiCall()
    }


}