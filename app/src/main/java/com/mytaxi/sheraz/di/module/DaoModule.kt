package com.mytaxi.sheraz.di.module

import com.mytaxi.sheraz.data.db.MyTaxiDatabase
import com.mytaxi.sheraz.data.db.dao.MyTaxiDao
import dagger.Module
import dagger.Provides

@Module
class DaoModule {

    @Provides
    fun provideMyTaxiDao(database: MyTaxiDatabase): MyTaxiDao = database.myTaxiDao()

}