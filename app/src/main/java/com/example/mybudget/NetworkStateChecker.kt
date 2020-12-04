package com.example.mybudget

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Isra
 */
public fun checkNetworkConnection(context: Context): Boolean{
    val connectivityManager = context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}