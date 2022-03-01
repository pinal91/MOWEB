package com.example.moweb.viewmodel

import android.app.Dialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moweb.model.Items
import com.example.moweb.model.ProductitemResponse
import com.example.moweb.repository.MainActivityRepository

class ProductListActivityViewModel : ViewModel() {

    var servicesProductListData: MutableLiveData<ProductitemResponse> = MainActivityRepository.serviceProductList


    fun getproductList(cat_id: String, progressDialog: Dialog?) {
        MainActivityRepository.getProductListApiCall(cat_id,progressDialog)
    }

    fun deleteProductList(context: Context, items: ArrayList<String?>?) {
        MainActivityRepository.deleteProducts(context,items)
    }

    fun insertProductData(context: Context, items_data: MutableList<Items?>?) {
        MainActivityRepository.insertProductData(context, items_data)
    }
}