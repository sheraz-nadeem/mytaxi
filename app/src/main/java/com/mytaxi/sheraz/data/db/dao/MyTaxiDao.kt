package com.mytaxi.sheraz.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mytaxi.sheraz.data.db.entity.MyTaxiEntry

@Dao
interface MyTaxiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(myTaxiEntry: MyTaxiEntry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(myTaxiList: List<MyTaxiEntry>)

    @Query("SELECT * FROM my_taxi")
    fun getAllTaxiList(): LiveData<List<MyTaxiEntry>>

    @Query("SELECT * FROM my_taxi WHERE fleetType = 'POOLING'")
    fun getMyTaxiListAvailable(): LiveData<List<MyTaxiEntry>>

    @Query("DELETE FROM my_taxi")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM my_taxi")
    fun getNumOfRows(): Int
}