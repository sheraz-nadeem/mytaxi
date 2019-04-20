package com.mytaxi.sheraz.di.module

import com.mytaxi.sheraz.data.CoroutinesDispatcherProvider
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class CoroutinesDispatcherProviderModule {

    @Provides
    fun provideCoroutinesDispatcherProvider(): CoroutinesDispatcherProvider = CoroutinesDispatcherProvider(
        Dispatchers.Main,
        Dispatchers.IO,
        Dispatchers.Default
    )
}