package com.example.moweb.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moweb.R
import com.example.moweb.model.CategoryListItem
import kotlinx.android.synthetic.main.list_loading_view.view.*
import kotlinx.android.synthetic.main.product_list_item.view.*


class CategoryListAdapter(
    private val dataList: MutableList<CategoryListItem?>,
    private val context: Activity?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    override fun getItemCount(): Int = dataList.count()
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            VIEW_TYPE_ITEM -> {
                if (dataList[position]!!.category != "") {
                    holder.itemView.txtProductname.visibility = View.VISIBLE
                    holder.itemView.txtProductname.text = dataList.get(position)!!.category

                } else {
                    holder.itemView.txtProductname.visibility = View.INVISIBLE
                }
                holder.itemView.txtProductname.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11.0F);
                Glide.with(context!!)
                    .load("http://3.208.241.167:4000/" + dataList[position]?.image_url)
                    .into(holder.itemView.imgProduct)
                holder.itemView.relMain.setOnClickListener {
                    context.startActivity(Intent(context,ProductListActivity::class.java).putExtra("cat_id", dataList[position]?._id))
                }

            }

        }
    }

    private fun getViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater
    ): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        var v1: View? = null

        v1 = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)

        return ViewHolder(v1!!, parent.context as Activity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        /*      val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)

              return ViewHolder(view, parent.context as Activity)*/

        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            VIEW_TYPE_ITEM -> viewHolder = getViewHolder(parent, inflater)
        }
        return viewHolder!!
    }


    class ViewHolder(view: View, val activity: Activity) : RecyclerView.ViewHolder(view) {


    }

    private fun add(mc: CategoryListItem) {
        dataList.add(mc)
        notifyItemInserted(dataList.size - 1)
    }

    fun addAll(mcList: MutableList<CategoryListItem?>) {
        dataList.addAll(mcList)
        notifyDataSetChanged()
    }

    fun remove(city: CategoryListItem) {
        val position = dataList.indexOf(city)
        if (position > -1) {
            dataList.removeAt(position)
            notifyItemRemoved(position)
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == dataList.size - 1) VIEW_TYPE_ITEM else VIEW_TYPE_ITEM
    }

}