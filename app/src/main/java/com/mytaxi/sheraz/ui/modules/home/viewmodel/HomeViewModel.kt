package com.mytaxi.sheraz.ui.modules.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mytaxi.sheraz.data.db.dao.MyTaxiDao
import com.mytaxi.sheraz.data.db.entity.MyTaxiEntry
import com.mytaxi.sheraz.data.repository.MyTaxiListByLocationRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val myTaxiDao: MyTaxiDao,
    private val myTaxiListByLocationRepository: MyTaxiListByLocationRepository
) : ViewModel() {

    fun getMyTaxis(pOneLat: String, pOneLng: String, pTwoLat: String, pTwoLng: String) {

        Log.d(TAG, "getMyTaxis(): pOneLat: $pOneLat, pOneLng: $pOneLng, pTwoLat: $pTwoLat, pTwoLng: $pTwoLng")
        myTaxiListByLocationRepository.getMyTaxiListByLocation(pOneLat, pOneLng, pTwoLat, pTwoLng)
    }

    fun getMyAllTaxisLiveData(): LiveData<List<MyTaxiEntry>> {
        return myTaxiDao.getAllTaxiList()
    }

    fun getMyTaxisAvailableLiveData(): LiveData<List<MyTaxiEntry>> {
        return myTaxiDao.getMyTaxiListAvailable()
    }


    /**
     * Companion object, common to all instances of this class
     * Similar to static fields in Java
     */
    companion object {
        private val TAG: String = HomeViewModel::class.java.simpleName
    }

}
