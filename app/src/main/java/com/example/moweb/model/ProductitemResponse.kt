
package com.example.moweb.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.moweb.util.Converters

data class ProductitemResponse(
	val status_code: Int? = null,
	val response: MutableList<Items?>? = null,
	val message: String? = null,
	val cart_quantity: Int? = null
)

@Entity(tableName = "items")
data class Items(
	@ColumnInfo(name = "product_name")
	val product_name: String? = null,

	@ColumnInfo(name = "size")
	val size: String? = null,

	@TypeConverters(Converters::class)
	@ColumnInfo(name = "product_image")
	val product_image: ArrayList<String?>? = null,

	@ColumnInfo(name = "main_price")
	val main_price:String?  = null,

	@ColumnInfo(name = "special_price")
	val special_price: String?  = null,

	@ColumnInfo(name = "_id")
	val _id:String?=null,


	@ColumnInfo(name = "category_id")
	var category_id:String
)
{

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "pid")
	var pId: Int? = null

}

