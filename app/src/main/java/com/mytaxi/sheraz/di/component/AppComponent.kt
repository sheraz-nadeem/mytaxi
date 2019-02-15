package com.mytaxi.sheraz.di.component

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.mytaxi.sheraz.data.db.MyTaxiDatabase
import com.mytaxi.sheraz.data.db.dao.MyTaxiDao
import com.mytaxi.sheraz.data.network.MyTaxiApiService
import com.mytaxi.sheraz.data.network.MyTaxiListByLocationNetworkDataSource
import com.mytaxi.sheraz.data.repository.MyTaxiListByLocationRepository
import com.mytaxi.sheraz.di.module.*
import com.mytaxi.sheraz.ui.modules.home.view.HomeFragmentModule
import com.mytaxi.sheraz.ui.modules.home.viewmodel.HomeViewModel
import com.squareup.picasso.Picasso
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        DaoModule::class,
        RepositoryModule::class,
        HomeFragmentModule::class
    ]
)
interface AppComponent {

    fun appContext(): Context
    fun database(): MyTaxiDatabase
    fun myTaxiDao(): MyTaxiDao
    fun retrofit(): Retrofit
    fun picasso(): Picasso
    fun myTaxiApiService(): MyTaxiApiService
    fun myTaxiListByLocationNetworkDataSource(): MyTaxiListByLocationNetworkDataSource
    fun myTaxiListByLocationRepository(): MyTaxiListByLocationRepository
    fun homeViewModel(): HomeViewModel
    fun viewModelFactory(): ViewModelProvider.Factory

}