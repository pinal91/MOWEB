package com.example.moweb.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.moweb.model.CategoryResponse
import com.example.moweb.model.ProductitemResponse
import com.example.moweb.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MainActivityRepository {

    val serviceProduct = MutableLiveData<CategoryResponse>()
    val serviceProductList = MutableLiveData<ProductitemResponse>()



    fun getProductApiCall(): MutableLiveData<CategoryResponse> {

        val call = RetrofitClient.apiInterface.getProduct()

        call.enqueue(object: Callback<CategoryResponse> {
            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", response.body().toString())

                response.body()?.let {
                    serviceProduct.postValue(it)
                }

            }
        })

        return serviceProduct
    }

    fun getProductListApiCall(cat_id:String): MutableLiveData<ProductitemResponse> {

        val call = RetrofitClient.apiInterface.getProductList(cat_id)

        call.enqueue(object: Callback<ProductitemResponse> {
            override fun onFailure(call: Call<ProductitemResponse>, t: Throwable) {
                // TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<ProductitemResponse>,
                response: Response<ProductitemResponse>
            ) {
                // TODO("Not yet implemented")

                response.body()?.let {
                    serviceProductList.postValue(it)
                }

            }
        })

        return serviceProductList
    }
}