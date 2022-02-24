package com.example.moweb.model

data class CategoryResponse(
	val response: Response? = null,
	val message: String? = null
)

data class Response(
	val categoryList: MutableList<CategoryListItem?>? = null,

)

data class CategoryListItem(
	val image_url: String? = null,
	val _id: String? = null,
	val category: String? = null
)
