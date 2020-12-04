package com.example.mybudget;

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley


/**
 * Created by Isra on 01/11/2020.
 */

class VolleySingleton private constructor(context: Context) {
    private var mRequestQueue: RequestQueue?
    fun getRequestQueue(): RequestQueue? {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx?.applicationContext)
        }
        return mRequestQueue
    }

    fun <T> addToRequestQueue(req: Request<T>?) {
        getRequestQueue()!!.add(req)
    }

    companion object {
        private var mInstance: VolleySingleton? = null
        private var mCtx: Context? = null
        @Synchronized
        fun getInstance(context: Context): VolleySingleton? {
            if (mInstance == null) {
                mInstance = VolleySingleton(context)
            }
            return mInstance
        }
    }

    init {
        mCtx = context
        mRequestQueue = getRequestQueue()
    }
}