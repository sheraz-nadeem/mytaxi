package com.mytaxi.sheraz

import android.app.Application
import android.util.Log
import com.mytaxi.sheraz.di.component.AppComponent
import com.mytaxi.sheraz.di.component.DaggerAppComponent
import com.mytaxi.sheraz.di.module.ContextModule


class MyTaxiApplication : Application() {

    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        Log.d(TAG, "onCreate: ")
        super.onCreate()
        INSTANCE = this
        component = DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }

    companion object {
        private val TAG = MyTaxiApplication::class.java.simpleName

        private var INSTANCE: MyTaxiApplication? = null

        fun get(): MyTaxiApplication = INSTANCE!!
    }
}

class Injector private constructor() {
    companion object {
        fun get() : AppComponent = MyTaxiApplication.get().component
    }
}