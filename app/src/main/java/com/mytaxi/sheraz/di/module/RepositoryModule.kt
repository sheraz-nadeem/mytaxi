package com.mytaxi.sheraz.di.module

import com.mytaxi.sheraz.data.CoroutinesDispatcherProvider
import com.mytaxi.sheraz.data.db.dao.MyTaxiDao
import com.mytaxi.sheraz.data.network.MyTaxiListByLocationNetworkDataSource
import com.mytaxi.sheraz.data.repository.MyTaxiListByLocationRepository
import com.mytaxi.sheraz.data.repository.MyTaxiListByLocationRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMyTaxiListByLocationRepository(
        dao: MyTaxiDao,
        networkDataSource: MyTaxiListByLocationNetworkDataSource,
        dispatcherProvider: CoroutinesDispatcherProvider
    ): MyTaxiListByLocationRepository = MyTaxiListByLocationRepositoryImpl(dao, networkDataSource, dispatcherProvider)
}