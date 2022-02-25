package com.example.moweb.view

import android.app.Activity
import android.graphics.Paint
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moweb.R
import com.example.moweb.db.DatabaseHelper
import com.example.moweb.model.Items
import kotlinx.android.synthetic.main.category_product_items.view.*
import java.util.*


class ProductListAdapter(private val dataList: MutableList<Items?>, private val context: Activity?
,private var addlisner: ProductListActivity.addItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),Filterable {

    private val VIEW_TYPE_ITEM = 0
    lateinit var productsDBHelper: DatabaseHelper
    private var itemsModelListFiltered: MutableList<Items?>?= dataList
    override fun getItemCount(): Int = itemsModelListFiltered!!.count()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        productsDBHelper = DatabaseHelper(context!!)
        when (getItemViewType(position)) {
            VIEW_TYPE_ITEM -> {

                holder.itemView.txtProductname.text= itemsModelListFiltered!![position]!!.product_name
                holder.itemView.txtUnit.text= itemsModelListFiltered!![position]!!.size
                holder.itemView.txtProductname.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11.0F)
                holder.itemView.txtFinalprice.text="TZS "+ itemsModelListFiltered!![position]!!.special_price
                holder.itemView.txtRegularprice.text="TZS "+ itemsModelListFiltered!![position]!!.main_price

                holder.itemView.txtRegularprice.paintFlags = holder.itemView.txtRegularprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
               /*                 if (dataList[position]?.main_price!= null && dataList[position]?.special_price!= null) {
                                    if (dataList[position]?.main_price.equals(dataList[position]?.special_price)
                                        || java.lang.Float.parseFloat(dataList[position]?.main_price!!) < java.lang.Float.parseFloat(
                                            dataList[position]?.special_price!!)) {
                                        holder.itemView.txtRegularprice.visibility = View.INVISIBLE
                                    } else {
                                        holder.itemView.txtRegularprice.visibility = View.VISIBLE

                                    }
                                } else {

                                    holder.itemView.txtRegularprice.visibility = View.VISIBLE

                                }*/

                if (context != null&& itemsModelListFiltered!![position]!!.product_image?.isNotEmpty()!!) {
                    Glide.with(context).load("http://3.208.241.167:4000"+ (itemsModelListFiltered!![position]!!.product_image?.get(0) ?: ""))
                        .into(holder.itemView.imgProduct)
                }

                holder.itemView.add.setOnClickListener {
                    var qnty=productsDBHelper.getQtyInCart(itemsModelListFiltered!![position]!!._id!!).toInt()+1
                    addlisner.onaddClick(position, itemsModelListFiltered!![position]!!, qnty.toString())

                }

                holder.itemView.minus.setOnClickListener {
                    var qnty=productsDBHelper.getQtyInCart(itemsModelListFiltered!!.get(position)!!._id!!).toInt()-1
                    addlisner.onaddClick(position, itemsModelListFiltered!![position]!!, qnty.toString())
                }

                holder.itemView.linAdd.setOnClickListener {
                    addlisner.onaddClick(position, itemsModelListFiltered!![position]!!,"1")

                }
                if (productsDBHelper.isItemInCart(itemsModelListFiltered!!.get(position)!!._id!!)) {

                    holder.itemView.txtQunty.text=productsDBHelper.getQtyInCart(
                        itemsModelListFiltered!!.get(position)!!._id!!)
                    holder.itemView.linQnty.visibility=View.VISIBLE
                    holder.itemView.linAdd.visibility=View.GONE
                } else {
                    holder.itemView.linAdd.visibility=View.VISIBLE
                    holder.itemView.linQnty.visibility=View.GONE
                }
            }

        }
    }

    private fun getViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        var v1: View? = null
        v1 = LayoutInflater.from(parent.context).inflate(R.layout.category_product_items, parent, false)
        return ViewHolder(v1!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            VIEW_TYPE_ITEM -> viewHolder = getViewHolder(parent, inflater)

        }
        return viewHolder!!
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    private fun add(mc: Items) {
        dataList.add(mc)
        notifyItemInserted(dataList.size - 1)
    }

    fun addAll(mcList: MutableList<Items?>) {
        dataList.addAll(mcList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == dataList.size - 1 ) VIEW_TYPE_ITEM else VIEW_TYPE_ITEM
    }

    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val filterResults = FilterResults()
                if (constraint.isEmpty()) {
                    filterResults.count = dataList.size
                    filterResults.values = dataList
                } else {
                    val resultsModel: MutableList<Items> = ArrayList()
                    val searchStr = constraint.toString().lowercase(Locale.getDefault())
                    for (itemsModel in dataList) {
                        if (itemsModel != null) {
                            if (itemsModel.product_name!!.lowercase(Locale.getDefault()).startsWith(searchStr)) {
                                resultsModel.add(itemsModel)
                            }
                        }
                        filterResults.count = resultsModel.size
                        filterResults.values = resultsModel
                    }
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                itemsModelListFiltered = results.values as MutableList<Items?>
                notifyDataSetChanged()
            }
        }
    }

}