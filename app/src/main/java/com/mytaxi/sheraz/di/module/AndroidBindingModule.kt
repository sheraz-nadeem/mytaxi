package com.mytaxi.sheraz.di.module

import com.mytaxi.sheraz.ui.modules.home.view.HomeActivity
import com.mytaxi.sheraz.ui.modules.home.view.HomeFragment
import com.mytaxi.sheraz.ui.modules.home.view.HomeFragmentModule
import com.mytaxi.sheraz.ui.modules.home.view.MapFragment
import com.mytaxi.sheraz.ui.modules.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidBindingModule {

    /* Activities */

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun splashActivity() : SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun homeActivity() : HomeActivity


    /* Fragments */

    @ContributesAndroidInjector(modules = [(HomeFragmentModule::class)])
    abstract fun homeFragment() : HomeFragment

    @ContributesAndroidInjector(modules = [(HomeFragmentModule::class)])
    abstract fun mapFragment() : MapFragment
}