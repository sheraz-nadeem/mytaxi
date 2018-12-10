package com.mytaxi.sheraz.ui.modules.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mytaxi.sheraz.data.db.dao.MyTaxiDao
import com.mytaxi.sheraz.data.repository.MyTaxiListByLocationRepository


class HomeViewModelFactory(
    private val myTaxiDao: MyTaxiDao,
    private val myTaxiListByLocationRepository: MyTaxiListByLocationRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(myTaxiDao, myTaxiListByLocationRepository) as T
    }
}