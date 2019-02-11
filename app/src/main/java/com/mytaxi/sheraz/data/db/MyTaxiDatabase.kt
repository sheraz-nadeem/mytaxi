package com.mytaxi.sheraz.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mytaxi.sheraz.data.db.dao.MyTaxiDao
import com.mytaxi.sheraz.data.db.entity.MyTaxiEntry

@Database(
    entities = [MyTaxiEntry::class],
    version = 1,
    exportSchema = false // Just to get rid of the warning generated at build time
)

abstract class MyTaxiDatabase : RoomDatabase() {
    // All Dao Methods
    abstract fun myTaxiDao(): MyTaxiDao

    /**
     * Companion object, common to all instances of this class
     * Similar to static fields in Java
     */
    companion object {
//        @Volatile
//        private var instance: MyTaxiDatabase? = null
//        private val LOCK = Any()
//
//        // Below invoke function, implemented as an "operator function" which
//        // allows us to get instance like => "MyTaxiDatabase()"
//        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
//            instance ?: buildDatabase(context).also { instance = it }
//        }
//
//        private fun buildDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                MyTaxiDatabase::class.java, "my_taxi_db"
//            ).build()

    }
}