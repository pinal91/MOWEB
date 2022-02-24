package com.example.moweb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moweb.model.CategoryResponse
import com.example.moweb.model.ProductitemResponse
import com.example.moweb.repository.MainActivityRepository

class ProductListActivityViewModel : ViewModel() {

    var servicesProductListData: MutableLiveData<ProductitemResponse>? = null

    init {

        servicesProductListData = MainActivityRepository.serviceProductList
    }



    fun getproductList(cat_id:String) {
        MainActivityRepository.getProductListApiCall(cat_id)
    }

}