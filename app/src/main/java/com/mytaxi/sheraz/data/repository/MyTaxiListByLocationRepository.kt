package com.mytaxi.sheraz.data.repository

import androidx.lifecycle.LiveData
import com.mytaxi.sheraz.data.db.entity.MyTaxiEntry

interface MyTaxiListByLocationRepository {
    fun getMyTaxiListByLocation(pOneLat: String, pOneLng: String, pTwoLat: String, pTwoLng: String)
    suspend fun fetchMyTaxiListByLocationFromNetwork(pOneLat: String, pOneLng: String, pTwoLat: String, pTwoLng: String)
}