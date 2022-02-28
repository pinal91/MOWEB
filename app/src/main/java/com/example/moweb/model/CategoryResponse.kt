package com.example.moweb.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class CategoryResponse(
	val response: Response? = null,
	val message: String? = null
)

data class Response(
	val categoryList: MutableList<CategoryListItem?>? = null,

)
@Entity(tableName = "cat_data")
data class CategoryListItem(
	@ColumnInfo(name = "image_url")
	val image_url: String? = null,

	@ColumnInfo(name = "_id")
	val _id: String? = null,

	@ColumnInfo(name = "category")
	val category: String? = null,

)
{

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	var Id: Int? = null

}
