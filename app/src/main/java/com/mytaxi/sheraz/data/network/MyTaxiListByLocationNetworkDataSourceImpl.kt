package com.mytaxi.sheraz.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mytaxi.sheraz.data.network.response.TaxiListResponse
import com.mytaxi.sheraz.internal.NoConnectivityException

class MyTaxiListByLocationNetworkDataSourceImpl(
    private val myTaxiApiService: MyTaxiApiService
) : MyTaxiListByLocationNetworkDataSource {

    /**
     * Encapsulating Mutable value so that it can be only changed within the "fetchMyTaxiListByLocation()" method
     */
    private val _downloadedMyTaxiListByLocation = MutableLiveData<TaxiListResponse>()
    override val downloadedMyTaxiListByLocation: LiveData<TaxiListResponse>
        get() = _downloadedMyTaxiListByLocation // MutableLiveData extends LiveData, so it will be automatically cast to LiveData


    override suspend fun fetchMyTaxiListByLocation(pOneLat: String, pOneLng: String, pTwoLat: String, pTwoLng: String) {

        Log.d(TAG, "fetchMyTaxiListByLocation(): pOneLat: $pOneLat, pOneLng: $pOneLng, pTwoLat: $pTwoLat, pTwoLng: $pTwoLng")

        try {

            val fetchedMyTaxiListByLocation = myTaxiApiService
                .getMyTaxiListByLocation(pOneLat, pOneLng, pTwoLat, pTwoLng)
                .await()

            Log.v(TAG, "fetchMyTaxiListByLocation(): fetchedMyTaxiListByLocation: ${fetchedMyTaxiListByLocation.poiList.size}")
            Log.v(TAG, "fetchMyTaxiListByLocation(): fetchedMyTaxiListByLocation: ${fetchedMyTaxiListByLocation.poiList}")
            // MutableLiveData.postValue must be called from background thread,
            // that is why this function is a "suspend" function which will
            // be called from a coroutine.
            // Finally, postValue will post a task on "main thread" to set the
            // given value
            _downloadedMyTaxiListByLocation.postValue(fetchedMyTaxiListByLocation)

        } catch (e: NoConnectivityException) {
            Log.e(TAG, "fetchMyTaxiListByLocation(): Internet Connectivity Exception", e)
        }
    }

    /**
     * Companion object, common to all instances of this class
     * Similar to static fields in Java
     */
    companion object {
        private val TAG: String = MyTaxiListByLocationNetworkDataSourceImpl::class.java.simpleName
    }
}