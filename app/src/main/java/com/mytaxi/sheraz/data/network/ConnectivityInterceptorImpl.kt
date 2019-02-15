package com.mytaxi.sheraz.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.mytaxi.sheraz.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(
    context: Context
) : ConnectivityInterceptor {

    init {
        Log.d(TAG, "init(): ")
    }

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain) : Response  {
        if (!isOnline())
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }

    private fun isOnline(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }



    /**
     * Companion object, common to all instances of this class
     * Similar to static fields in Java
     */
    companion object {
        private val TAG: String = ConnectivityInterceptorImpl::class.java.simpleName
    }
}