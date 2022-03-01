package com.example.moweb.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moweb.model.CategoryListItem
import com.example.moweb.model.Items
import com.example.moweb.util.Converters

@Database(
    entities = [CategoryListItem::class,Items::class],
    version = 13,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DataBase : RoomDatabase() {

    abstract fun cat_dao(): CatDao?


    companion object {
        @Volatile
        private var instance: DataBase? = null

        fun getDatabase(context: Context): DataBase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, DataBase::class.java, "category_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}
