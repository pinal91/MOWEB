package com.example.moweb.roomdb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moweb.model.CategoryListItem

@Dao
interface CatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(postData: MutableList<CategoryListItem?>?)

    @Query("DELETE FROM cat_data")
    fun DeleteAll()

    @Query("SELECT * FROM cat_data ")
    fun getAllPostData(): LiveData<MutableList<CategoryListItem?>?>?
}