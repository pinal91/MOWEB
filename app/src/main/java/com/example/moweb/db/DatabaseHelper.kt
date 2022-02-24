package com.example.moweb.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)

    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)

        onCreate(db)
    }

    @Throws(SQLiteConstraintException::class)
    fun addItemToCart(product: AddToCartItemData): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put(PRODUCT_ID, product.product_id)
        values.put(QTY, product.qty)
        db.insert(TABLE_PRODUCTS, null, values)

        db.close()
        return true
    }


    //to check same item in cart or not
    @Throws(SQLiteConstraintException::class)
    fun isItemInCart(id: String): Boolean {
        val db = writableDatabase
        val selectString = "SELECT $PRODUCT_ID FROM $TABLE_PRODUCTS WHERE $PRODUCT_ID = ?"
        val cursor = db.rawQuery(selectString, arrayOf(id))
        var hasObject = false
        if (cursor.moveToFirst()) {
            hasObject = true
        }
        cursor.close()
        close()
        return hasObject
    }

    //get number of quantity from cart

    @SuppressLint("Range")
    fun getQtyInCart(productID: String): String {
        val db = writableDatabase
        var qty = ""
        val c = db.rawQuery("select DISTINCT $QTY from $TABLE_PRODUCTS where $PRODUCT_ID='$productID'", null)
        if (c.moveToFirst())
            qty = c.getString(c.getColumnIndex(QTY))
        c.close()
        close()
        return qty
    }

    //update product


    @Throws(SQLiteConstraintException::class)
    fun updateProductsInCart(strQty: String, productID: String?) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(QTY, strQty)

        db.update(TABLE_PRODUCTS, values, "$PRODUCT_ID = ? ", arrayOf(productID))
        //  db.update(TABLE_PRODUCTS, values, "$PRODUCT_ID = $productID AND $PRODUCT_DBOPTION =$productDbOPtion", null)

        close()
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteProductFromCart(productID: String): Boolean {
        val db = writableDatabase
        val selection = "$PRODUCT_ID LIKE ?"
        val selectionArgs = arrayOf(productID)
        db.delete(TABLE_PRODUCTS, selection, selectionArgs)
        return true
    }


    @SuppressLint("Range")
    @Throws(SQLiteConstraintException::class)
    fun getAllCartItem(): ArrayList<AddToCartItemData> {
        val arrListAllCartProducts = ArrayList<AddToCartItemData>()
        val db = writableDatabase
        var cursor: Cursor? = null
        cursor = try {
            db.rawQuery("select * from " + TABLE_PRODUCTS, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }
        var product_id: String
        var qty:String

        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {

                product_id = cursor.getString(cursor.getColumnIndex(PRODUCT_ID))
                qty=cursor.getString(cursor.getColumnIndex(QTY))

                arrListAllCartProducts.add(
                    AddToCartItemData(product_id,qty)
                )
                cursor.moveToNext()
            }
        }
        return arrListAllCartProducts
    }


    //GET ALL CART PRODUCT ID
    @SuppressLint("Range")
    @Throws(SQLiteConstraintException::class)
    fun getAllCartProductIDs(): java.util.ArrayList<String> {
        val allCartProductIDs = java.util.ArrayList<String>()
        val db = writableDatabase
        var cursor: Cursor? = try {
            db.rawQuery("select * from $TABLE_PRODUCTS", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return java.util.ArrayList()
        }
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                allCartProductIDs.add(cursor.getString(cursor.getColumnIndex(PRODUCT_ID)))
                cursor.moveToNext()
            }
        }
        cursor.close()
        close()
        return allCartProductIDs
    }




    companion object {
        private const val DATABASE_VERSION = 4

        private val DATABASE_NAME = "Vegii.db"
        val TABLE_PRODUCTS = "shoppingcart"
        val ID = "_id"
        val PRODUCT_ID = "id"
        val QTY="qty"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                        PRODUCT_ID + " TEXT," +  QTY +" TEXT)"



        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_PRODUCTS


    }
}
