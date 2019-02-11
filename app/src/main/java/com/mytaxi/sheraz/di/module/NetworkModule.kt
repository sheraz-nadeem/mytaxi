package com.mytaxi.sheraz.di.module

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mytaxi.sheraz.data.network.*
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideRequestInterceptor(context: Context) : ConnectivityInterceptor {
        return ConnectivityInterceptorImpl(context)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context, requestInterceptor: ConnectivityInterceptor) : OkHttpClient {
        val cache = Cache(context.cacheDir, CACHE_SIZE)
        return OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .cache(cache)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://fake-poi-api.mytaxi.com/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttp3Downloader(okHttpClient: OkHttpClient) = OkHttp3Downloader(okHttpClient)

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(MyTaxiApiService::class.java)

    @Provides
    @Singleton
    fun providePicasso(context: Context, okHttp3Downloader: OkHttp3Downloader) = Picasso.Builder(context).downloader(okHttp3Downloader).build()

    @Provides
    @Singleton
    fun provideNetworkDataSource(myTaxiApiService: MyTaxiApiService) : MyTaxiListByLocationNetworkDataSource {
        return MyTaxiListByLocationNetworkDataSourceImpl(myTaxiApiService)
    }

    companion object {
        private const val CACHE_SIZE: Long = 10 * 1024 * 1024 // 10MB
    }
}