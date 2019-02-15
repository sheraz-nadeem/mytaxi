package com.mytaxi.sheraz.data.db

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import com.mytaxi.sheraz.data.db.dao.MyTaxiDao
import com.mytaxi.sheraz.data.db.entity.MyTaxiEntry

@Database(
    entities = [MyTaxiEntry::class],
    version = 1,
    exportSchema = false // Just to get rid of the warning generated at build time
)

abstract class MyTaxiDatabase : RoomDatabase() {

    init {
        Log.d(TAG, "init(): ")
    }


    // All Dao Methods
    abstract fun myTaxiDao(): MyTaxiDao



    /**
     * Companion object, common to all instances of this class
     * Similar to static fields in Java
     */
    companion object {
        private val TAG: String = MyTaxiDatabase::class.java.simpleName
    }
}