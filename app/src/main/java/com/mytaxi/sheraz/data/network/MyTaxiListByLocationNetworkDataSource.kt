package com.mytaxi.sheraz.data.network

import androidx.lifecycle.LiveData
import com.mytaxi.sheraz.data.network.response.TaxiListResponse

interface MyTaxiListByLocationNetworkDataSource {
    val downloadedMyTaxiListByLocation: LiveData<TaxiListResponse>

    suspend fun fetchMyTaxiListByLocation(
        pOneLat: String,
        pOneLng: String,
        pTwoLat: String,
        pTwoLng: String
    )
}