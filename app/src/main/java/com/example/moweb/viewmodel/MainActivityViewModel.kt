package com.example.moweb.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moweb.model.CategoryListItem
import com.example.moweb.model.CategoryResponse
import com.example.moweb.model.Items
import com.example.moweb.repository.MainActivityRepository

class MainActivityViewModel : ViewModel() {

    var servicesProductData: MutableLiveData<CategoryResponse>? = null


    init {
        servicesProductData = MainActivityRepository.serviceProduct
    }

    fun getproduct() {
        MainActivityRepository.getProductApiCall()
    }

    fun getoffLineproduct(context: Context): LiveData<MutableList<CategoryListItem?>?>? {
        return MainActivityRepository.getdata(context)
    }


    fun insertData(context: Context, cat_data: MutableList<CategoryListItem?>?) {
        MainActivityRepository.insertData(context, cat_data)
    }


    fun deleteAll(context: Context) {
        MainActivityRepository.deleteData(context)
    }

}