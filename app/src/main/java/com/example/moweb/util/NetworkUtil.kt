package com.example.moweb.util
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

/**
 * Created by pinal-leza on 1/18/2018.
 */

object NetworkUtil {
    private val TYPE_WIFI = 1
    private val TYPE_MOBILE = 2
    private val TYPE_NOT_CONNECTED = 0

    @SuppressLint("MissingPermission")
    fun getConnectivityStatus(context: Context): Int {
        val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (null != activeNetwork) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI
            if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE
        } else {
            Toast.makeText(context,"Please check your internet connection",Toast.LENGTH_LONG).show()
        }
        return TYPE_NOT_CONNECTED
    }

}