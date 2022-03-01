package com.example.moweb.repository

import android.app.Dialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moweb.model.CategoryListItem
import com.example.moweb.model.CategoryResponse
import com.example.moweb.model.Items
import com.example.moweb.model.ProductitemResponse
import com.example.moweb.retrofit.RetrofitClient
import com.example.moweb.roomdb.DataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MainActivityRepository {

    val serviceProduct = MutableLiveData<CategoryResponse>()
    val serviceProductList = MutableLiveData<ProductitemResponse>()
    var dataBase: DataBase?=null
    var catDataModel: LiveData<MutableList<CategoryListItem?>?>? = null

    private fun initializeDB(context: Context): DataBase {
        return DataBase.getDatabase(context)
    }

    fun insertData(context: Context, cat_Data: MutableList<CategoryListItem?>?) {
        dataBase = initializeDB(context)

        CoroutineScope(Dispatchers.IO).launch {
            //val loginDetails = CategoryItemsData(cat_Data[])
            dataBase!!.cat_dao()!!.insert(cat_Data)
        }
    }

    fun insertProductData(context: Context, items_Data: MutableList<Items?>?) {
        dataBase = initializeDB(context)

        CoroutineScope(Dispatchers.IO).launch {
            //val loginDetails = CategoryItemsData(cat_Data[])
            dataBase!!.cat_dao()!!.insertItems(items_Data)
        }
    }

    fun getdata(context: Context):LiveData<MutableList<CategoryListItem?>?>? {
        dataBase = initializeDB(context)
        catDataModel = dataBase!!.cat_dao()?.getAllPostData()
        return catDataModel
    }

    fun deleteData(context: Context) {
        dataBase = initializeDB(context)

        CoroutineScope(Dispatchers.IO).launch {
            //val loginDetails = CategoryItemsData(cat_Data[])
            dataBase!!.cat_dao()!!.DeleteAll()
        }
    }

    fun deleteProducts(context: Context, items: ArrayList<String?>?) {
        dataBase = initializeDB(context)

        CoroutineScope(Dispatchers.IO).launch {
            //val loginDetails = CategoryItemsData(cat_Data[])
            dataBase!!.cat_dao()!!.delete(items)
           // dataBase!!.cat_dao()!!.deleteAllProduct()
        }
    }

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

    fun getProductListApiCall(cat_id: String, progressDialog: Dialog?): MutableLiveData<ProductitemResponse> {

        val call = RetrofitClient.apiInterface.getProductList(cat_id)

        call.enqueue(object: Callback<ProductitemResponse> {
            override fun onFailure(call: Call<ProductitemResponse>, t: Throwable) {
                // TODO("Not yet implemented")
                hideProgressDialog(progressDialog)
            }

            override fun onResponse(
                call: Call<ProductitemResponse>,
                response: Response<ProductitemResponse>
            ) {
                // TODO("Not yet implemented")

                response.body()?.let {
                    serviceProductList.postValue(it)
                }
                hideProgressDialog(progressDialog)

            }
        })

        return serviceProductList
    }


    private fun hideProgressDialog(progressDialog: Dialog?) {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }
}