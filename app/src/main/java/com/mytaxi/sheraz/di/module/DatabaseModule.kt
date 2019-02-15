package com.mytaxi.sheraz.di.module

import android.content.Context
import androidx.room.Room
import com.mytaxi.sheraz.data.db.MyTaxiDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): MyTaxiDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MyTaxiDatabase::class.java, "my_taxi_db"
        ).build()
    }

}