package com.example.moweb.retrofit

import com.example.moweb.model.CategoryResponse
import com.example.moweb.model.ProductitemResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {



    @GET("home_screen_list_grocery?app_uuid=d714957eb4675cf3")
    fun getProduct() : Call<CategoryResponse>

    @POST("get_sub_category")
    @FormUrlEncoded
    fun getProductList(@Field ("category_id") category_id:String) : Call<ProductitemResponse>

}