package com.example.moweb.view

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moweb.R
import com.example.moweb.databinding.ActivityProductListBinding
import com.example.moweb.db.AddToCartItemData
import com.example.moweb.db.DatabaseHelper
import com.example.moweb.model.Items
import com.example.moweb.util.DataBindingActivity
import com.example.moweb.viewmodel.MainActivityViewModel
import com.example.moweb.viewmodel.ProductListActivityViewModel
import kotlinx.android.synthetic.main.activity_product_list.*


class ProductListActivity : DataBindingActivity() {

    lateinit var context: Context
    private var adapterList: ProductListAdapter? = null
    lateinit var mainActivityViewModel: ProductListActivityViewModel
    private var products:MutableList<Items?>? = arrayListOf()
    private val mBinding: ActivityProductListBinding by binding(R.layout.activity_product_list)
    private var cat_id:String = ""
    lateinit var addlisner: addItemClickListener
    private lateinit var productsDBHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@ProductListActivity
        }

        context = this@ProductListActivity
        productsDBHelper = DatabaseHelper(this)
        addlisner = object : addItemClickListener {
            override fun onaddClick(int: Int, item: Items, qnty: String) {

                if (productsDBHelper.isItemInCart(item._id!!)) {

                    if (qnty == "0") {
                        productsDBHelper.deleteProductFromCart(item._id)
                        adapterList!!.notifyDataSetChanged()

                    } else {
                        productsDBHelper.updateProductsInCart(qnty, item._id)
                        adapterList!!.notifyDataSetChanged()


                    }
                } else {

                    var product = AddToCartItemData(item._id, qnty)
                    productsDBHelper.addItemToCart(product)
                    adapterList!!.notifyDataSetChanged()

                }

            }
        }
        cat_id = intent.getStringExtra("cat_id")!!
        var layoutManager: LinearLayoutManager? = null
        rcyProductList.setHasFixedSize(true)
        rcyProductList.itemAnimator
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        rcyProductList.layoutManager = layoutManager

        mainActivityViewModel = ViewModelProvider(this).get(ProductListActivityViewModel::class.java)

        wp7progressBar.showProgressBar()

        mainActivityViewModel.servicesProductListData?.observe(this) {
            products!!.clear()
            products!!.addAll(it.response!!)
            adapterList!!.notifyDataSetChanged()
        }


        adapterList = products?.let { ProductListAdapter(it, this,addlisner) }
        rcyProductList.adapter = adapterList

        mainActivityViewModel.getproductList(cat_id)


    }
    interface addItemClickListener {
        fun onaddClick(pos:Int,item: Items,qnty:String)
    }

}
