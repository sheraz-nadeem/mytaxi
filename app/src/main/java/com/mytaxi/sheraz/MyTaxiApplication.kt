package com.mytaxi.sheraz

import android.app.Application
import android.util.Log
import com.jakewharton.threetenabp.AndroidThreeTen
import com.mytaxi.sheraz.data.db.MyTaxiDatabase
import com.mytaxi.sheraz.data.network.*
import com.mytaxi.sheraz.data.repository.MyTaxiListByLocationRepository
import com.mytaxi.sheraz.data.repository.MyTaxiListByLocationRepositoryImpl
import com.mytaxi.sheraz.ui.modules.home.viewmodel.HomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MyTaxiApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyTaxiApplication))

        bind() from singleton { MyTaxiDatabase(instance()) }
        bind() from singleton { instance<MyTaxiDatabase>().myTaxiDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { MyTaxiApiService(instance()) }
        bind<MyTaxiListByLocationNetworkDataSource>() with singleton { MyTaxiListByLocationNetworkDataSourceImpl(instance()) }
        bind<MyTaxiListByLocationRepository>() with singleton { MyTaxiListByLocationRepositoryImpl(instance(), instance()) }
        bind() from provider { HomeViewModelFactory(instance(), instance()) }
    }

    override fun onCreate() {
        Log.d(TAG, "onCreate: ")
        super.onCreate()

        AndroidThreeTen.init(this)
    }



    companion object {
        private val TAG = MyTaxiApplication::class.java.simpleName
    }
}