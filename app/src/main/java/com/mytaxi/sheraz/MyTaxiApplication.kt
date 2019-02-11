package com.mytaxi.sheraz

import android.app.Application
import android.util.Log
import androidx.fragment.app.Fragment
import com.mytaxi.sheraz.di.component.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MyTaxiApplication : Application(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        Log.d(TAG, "onCreate: ")
        super.onCreate()

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    companion object {
        private val TAG = MyTaxiApplication::class.java.simpleName
    }
}