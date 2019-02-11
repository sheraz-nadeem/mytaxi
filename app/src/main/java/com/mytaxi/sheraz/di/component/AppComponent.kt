package com.mytaxi.sheraz.di.component

import android.app.Application
import com.mytaxi.sheraz.MyTaxiApplication
import com.mytaxi.sheraz.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        AndroidBindingModule::class,
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        DaoModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent : AndroidInjector<MyTaxiApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}