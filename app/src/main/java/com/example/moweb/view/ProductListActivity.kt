package com.example.moweb.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moweb.R
import com.example.moweb.databinding.ActivityProductListBinding
import com.example.moweb.db.AddToCartItemData
import com.example.moweb.db.DatabaseHelper
import com.example.moweb.model.Items
import com.example.moweb.util.DataBindingActivity
import com.example.moweb.viewmodel.ProductListActivityViewModel
import kotlinx.android.synthetic.main.activity_product_list.*


class ProductListActivity : DataBindingActivity() {

    lateinit var context: Context
    private var adapterList: ProductListAdapter? = null
    lateinit var mainActivityViewModel: ProductListActivityViewModel
    private var products: MutableList<Items?>? = arrayListOf()
    private val mBinding: ActivityProductListBinding by binding(R.layout.activity_product_list)
    private var cat_id: String = ""
    lateinit var addlisner: addItemClickListener
    private lateinit var productsDBHelper: DatabaseHelper
    private var searchResult:MutableList<Items?>? = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@ProductListActivity
        }

        context = this@ProductListActivity
        toolbarInitialize()

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
        txtToolbar.text=intent.getStringExtra("name")

        displayProgressDialog(context as ProductListActivity)
        var layoutManager: LinearLayoutManager? = null
        rcyProductList.setHasFixedSize(true)
        rcyProductList.itemAnimator
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcyProductList.layoutManager = layoutManager
        mainActivityViewModel = ViewModelProvider(this).get(ProductListActivityViewModel::class.java)
        localSearch()

        mainActivityViewModel.servicesProductListData?.observe(this) {
            products!!.clear()
            products!!.addAll(it.response!!)
            adapterList!!.notifyDataSetChanged()
        }


        adapterList = products?.let { ProductListAdapter(it, this, addlisner) }
        rcyProductList.adapter = adapterList
        mainActivityViewModel.getproductList(cat_id,progressDialog)



    }

    private fun localSearch() {
        imgClose.setOnClickListener {
            edtSearch.setText("")
        }

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //on text change call ws and check data is available or not
                try {
                    adapterList?.filter!!.filter(s);

                    if (s.isNotEmpty()) {
                        //cross button will visible if there is text in box
                        imgClose.visibility = View.VISIBLE
                    } else {
                        imgClose.visibility = View.GONE

                    }
                } catch (e: Exception) {
                }
            }
        })
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun toolbarInitialize() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val upArrow = resources.getDrawable(R.drawable.ic_nav_back_arrow)
        upArrow.setColorFilter(resources.getColor(R.color.bg_color), PorterDuff.Mode.SRC_ATOP)
        upArrow.isAutoMirrored = true
        upArrow.setVisible(true, true)
        supportActionBar!!.setHomeAsUpIndicator(upArrow)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    interface addItemClickListener {
        fun onaddClick(pos: Int, item: Items, qnty: String)
    }

    var progressDialog: Dialog? = null
    private fun displayProgressDialog(activity: Activity) {


        val v = activity.layoutInflater.inflate(R.layout.dialog_loading, null)
        progressDialog = Dialog(activity, R.style.MyTheme)
        progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog!!.setContentView(v)
        progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (progressDialog != null && !progressDialog!!.isShowing)
            progressDialog!!.show()
        progressDialog!!.setCancelable(false)
        progressDialog!!.setCanceledOnTouchOutside(false)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
