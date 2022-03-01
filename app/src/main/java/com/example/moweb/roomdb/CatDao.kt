package com.example.moweb.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moweb.model.CategoryListItem
import com.example.moweb.model.Items


@Dao
interface CatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(postData: MutableList<CategoryListItem?>?)

    @Query("DELETE FROM cat_data")
    fun DeleteAll()

    @Query("SELECT * FROM cat_data ")
    fun getAllPostData(): LiveData<MutableList<CategoryListItem?>?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(postData: MutableList<Items?>?)

    @Query("DELETE FROM items WHERE category_id IN (:category_id)")
    fun delete(category_id: ArrayList<String?>?)

    @Query("DELETE FROM items")
    fun deleteAllProduct()
}