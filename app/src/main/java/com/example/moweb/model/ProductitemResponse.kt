
package com.example.moweb.model

data class ProductitemResponse(
	val status_code: Int? = null,
	val response: MutableList<Items?>? = null,
	val message: String? = null,
	val cart_quantity: Int? = null
)

data class Items(
	val product_name: String? = null,
	val size: String? = null,
	val product_image: List<String?>? = null,
	val main_price:String?  = null,
	val special_price: String?  = null,
	val _id:String?=null
)

