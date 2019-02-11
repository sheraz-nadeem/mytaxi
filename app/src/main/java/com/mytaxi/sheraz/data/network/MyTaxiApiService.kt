package com.mytaxi.sheraz.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mytaxi.sheraz.data.network.response.TaxiListResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MyTaxiApiService {

    @GET(".")
    fun getMyTaxiListByLocation(
        @Query("p1Lat") pOneLat: String,
        @Query("p1Lon") pOneLng: String,
        @Query("p2Lat") pTwoLat: String,
        @Query("p2Lon") pTwoLng: String
    ): Deferred<TaxiListResponse>


//    companion object {
//        operator fun invoke(
//            connectivityInterceptor: ConnectivityInterceptor
//        ): MyTaxiApiService {
//
//            // Just to show off that Authorization header can be added
//            // using an Interceptor
////            val requestInterceptor = Interceptor { chain ->
////
////                val original = chain.request()
////                val newRequest = original.newBuilder()
////                    .addHeader("Authorization", "AUTH_TOKEN")
////                    .build()
////
////                return@Interceptor chain.proceed(newRequest)
////            }
//
//            val okHttpClient = OkHttpClient.Builder()
////                .addInterceptor(requestInterceptor)
//                .addInterceptor(connectivityInterceptor)
//                .build()
//
//            return Retrofit.Builder()
//                .client(okHttpClient)
//                .baseUrl("https://fake-poi-api.mytaxi.com/")
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(MyTaxiApiService::class.java)
//        }
//    }
}