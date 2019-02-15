package com.mytaxi.sheraz.data.repository

import android.util.Log
import com.mytaxi.sheraz.data.db.dao.MyTaxiDao
import com.mytaxi.sheraz.data.network.MyTaxiListByLocationNetworkDataSource
import com.mytaxi.sheraz.data.network.response.TaxiListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MyTaxiListByLocationRepositoryImpl(
    private val myTaxiDao: MyTaxiDao,
    private val myTaxiListByLocationNetworkDataSource: MyTaxiListByLocationNetworkDataSource
) : MyTaxiListByLocationRepository {

    init {
        Log.d(TAG, "init(): ")

        // Repository isn't lifecycle-aware, so we observer on NetworkDataSource "forever",
        // As soon as the data is fetched from the network it is persisted in DB immediately
        myTaxiListByLocationNetworkDataSource.downloadedMyTaxiListByLocation.observeForever { myTaxiList ->
            persistFetchedMyTaxiList(myTaxiList)
        }
    }

    private fun persistFetchedMyTaxiList(taxiListResponse: TaxiListResponse) {

        Log.d(TAG, "persistFetchedMyTaxiList(): taxiListResponse: $taxiListResponse")

        // Since Repository doesn't have a lifecycle we are only stuck
        // with GlobalScope.launch
        GlobalScope.launch(Dispatchers.IO) {
            myTaxiDao.insertList(taxiListResponse.poiList)
        }

    }

    override fun getMyTaxiListByLocation(pOneLat: String, pOneLng: String, pTwoLat: String, pTwoLng: String) {

        Log.d(
            TAG,
            "getMyTaxiListByLocation(): pOneLat: $pOneLat, pOneLng: $pOneLng, pTwoLat: $pTwoLat, pTwoLng: $pTwoLng"
        )

        // Since Repository doesn't have a lifecycle we are only stuck
        // with GlobalScope.launch
        GlobalScope.launch(Dispatchers.IO) {

            val numOfRows = myTaxiDao.getNumOfRows()
            Log.i(TAG, "getMyTaxiListByLocation(): numOfRows: $numOfRows")

            if (numOfRows > 0) {
                Log.v(
                    TAG,
                    "getMyTaxiListByLocation(): deleting existing data from table as API returns distinct result on each request..."
                )

                async { myTaxiDao.deleteAll() }.await()
            }

            fetchMyTaxiListByLocationFromNetwork(
                pOneLat, pOneLng, pTwoLat, pTwoLng
            )
        }

    }

    override suspend fun fetchMyTaxiListByLocationFromNetwork(
        pOneLat: String,
        pOneLng: String,
        pTwoLat: String,
        pTwoLng: String
    ) {

        Log.d(
            TAG,
            "fetchMyTaxiListByLocationFromNetwork(): pOneLat: $pOneLat, pOneLng: $pOneLng, pTwoLat: $pTwoLat, pTwoLng: $pTwoLng"
        )

        myTaxiListByLocationNetworkDataSource.fetchMyTaxiListByLocation(
            pOneLat, pOneLng, pTwoLat, pTwoLng
        )

    }


    /**
     * Companion object, common to all instances of this class
     * Similar to static fields in Java
     */
    companion object {
        private val TAG: String = MyTaxiListByLocationRepositoryImpl::class.java.simpleName
    }
}