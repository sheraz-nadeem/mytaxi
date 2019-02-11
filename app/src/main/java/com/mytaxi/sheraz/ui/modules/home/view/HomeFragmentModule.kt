package com.mytaxi.sheraz.ui.modules.home.view

import androidx.lifecycle.ViewModelProvider
import com.mytaxi.sheraz.ViewModelProviderFactory
import com.mytaxi.sheraz.data.db.dao.MyTaxiDao
import com.mytaxi.sheraz.data.repository.MyTaxiListByLocationRepository
import com.mytaxi.sheraz.ui.modules.home.viewmodel.HomeViewModel
import dagger.Module
import dagger.Provides

@Module
class HomeFragmentModule {

    @Provides
    fun provideViewModelFactory(homeViewModel: HomeViewModel): ViewModelProvider.Factory {
        return ViewModelProviderFactory(homeViewModel)
    }

    @Provides
    fun provideHomeViewModel(myTaxiDao: MyTaxiDao, myTaxiListByLocationRepository: MyTaxiListByLocationRepository) =
        HomeViewModel(myTaxiDao, myTaxiListByLocationRepository)

}