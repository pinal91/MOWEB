package com.example.moweb.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moweb.R
import com.example.moweb.databinding.ActivityMainBinding
import com.example.moweb.model.CategoryListItem

import com.example.moweb.util.DataBindingActivity
import com.example.moweb.util.NetworkUtil
import com.example.moweb.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : DataBindingActivity() {

    lateinit var context: Context
    private var adapterList: CategoryListAdapter? = null
    lateinit var mainActivityViewModel: MainActivityViewModel
    private var products: MutableList<CategoryListItem?>? = arrayListOf()
    private val mBinding: ActivityMainBinding by binding(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@MainActivity
        }

        context = this@MainActivity
        var layoutManager: GridLayoutManager? = null
        rcyProductList.setHasFixedSize(true)
        rcyProductList.itemAnimator
        layoutManager = GridLayoutManager(this, 3)
        rcyProductList.layoutManager = layoutManager

        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        if (NetworkUtil.getConnectivityStatus(this) == 0) {
            mainActivityViewModel.getoffLineproduct(context)!!.observe(this, Observer {
                Log.d("OFFLINE", it!![0]!!.category.toString())
                products = it
                products?.let { it -> adapterList!!.addAll(it) }
            })


        } else {

            mainActivityViewModel.deleteAll(this)

            wp7progressBar.showProgressBar()

           mainActivityViewModel.servicesProductData?.observe(this) {
                products = it.response!!.categoryList
                products?.let { it1 -> adapterList!!.addAll(it1) }
                mainActivityViewModel.insertData(this, products)
            }
        }


        adapterList = products?.let { CategoryListAdapter(it, this) }
        rcyProductList.adapter = adapterList

        mainActivityViewModel.getproduct()
    }


}
