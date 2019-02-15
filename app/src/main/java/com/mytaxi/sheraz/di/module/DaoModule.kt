package com.mytaxi.sheraz.di.module

import com.mytaxi.sheraz.data.db.MyTaxiDatabase
import dagger.Module
import dagger.Provides

@Module(includes = [
RepositoryModule::class
])
class DaoModule {

    @Provides
    fun provideMyTaxiDao(database: MyTaxiDatabase) = database.myTaxiDao()

}