package com.example.moweb.viewmodel

import android.app.Dialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moweb.model.ProductitemResponse
import com.example.moweb.repository.MainActivityRepository

class ProductListActivityViewModel : ViewModel() {

    var servicesProductListData: MutableLiveData<ProductitemResponse> = MainActivityRepository.serviceProductList


    fun getproductList(cat_id: String, progressDialog: Dialog?) {
        MainActivityRepository.getProductListApiCall(cat_id,progressDialog)
    }

}