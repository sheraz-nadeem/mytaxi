package com.mytaxi.sheraz.ui.modules.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mytaxi.sheraz.Injector
import com.mytaxi.sheraz.R
import com.mytaxi.sheraz.data.db.entity.MyTaxiEntry
import com.mytaxi.sheraz.ui.modules.home.fakemodel.FakeTaxiModel
import com.mytaxi.sheraz.ui.modules.home.view.HomeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random


abstract class ScopedFragment : Fragment(), CoroutineScope {

    /**
     * Abstract methods
     */
    abstract fun initUI()
    abstract fun fetchMyTaxiListAndObserve(): Job


    // Fake Data
    private val mFakeTaxiCarNames by lazy { return@lazy resources.getStringArray(R.array.fake_car_names) }
    private val mFakeTaxiDriverNames by lazy { return@lazy resources.getStringArray(R.array.fake_driver_names) }
    private val mFakeTaxiDriverProfileImages by lazy { return@lazy resources.obtainTypedArray(R.array.fake_driver_profile_images) }


    val mViewModelFactory: ViewModelProvider.Factory

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    /**
     * Fragment Lifecycle methods
     */

    init {
        Log.d(TAG, "init(): ")
        mViewModelFactory = Injector.get().viewModelFactory()
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d(TAG, "onCreate(): ")
        super.onCreate(savedInstanceState)
        job = Job()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.d(TAG, "onViewCreated(): ")
        super.onViewCreated(view, savedInstanceState)
        initUI()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        Log.d(TAG, "onActivityCreated(): ")
        super.onActivityCreated(savedInstanceState)
        fetchMyTaxiListAndObserve()
    }

    override fun onDestroy() {

        Log.d(TAG, "onDestroy(): ")
        super.onDestroy()
        job.cancel()

        // release typedArray as soon as we don't need it
        mFakeTaxiDriverProfileImages.recycle()

    }

    /**
     * Fake Data generation method
     */

    protected fun generateFakeTaxiData(list: List<MyTaxiEntry>): List<FakeTaxiModel.Taxi> {

        Log.d(HomeFragment.TAG, "generateFakeTaxiData(): list.size: ${list.size}")

        val fakeTaxiList = mutableListOf<FakeTaxiModel.Taxi>()

        for (index in list.indices) {

            val myTaxiEntry = list[index]

            val coordinate = myTaxiEntry.coordinate
            val driverImage = mFakeTaxiDriverProfileImages.getResourceId(index, R.drawable.ic_my_taxi_logo_splash)
            val driverName = mFakeTaxiDriverNames[index]
            val driverRating = Random.nextInt(1, 5)
            val fleetType = myTaxiEntry.fleetType
            val heading = myTaxiEntry.heading
            val id = myTaxiEntry.taxiId

            val randomDistanceFromUser = Random.nextInt(1, 25)
            val vehicleDistanceFromUser = "$randomDistanceFromUser KM"

            val randomEstimatedTimeOfArrival = Random.nextInt(1, 35)
            val vehicleEstimatedTimeOfArrival = "$randomEstimatedTimeOfArrival mins away"
            val vehicleModel = mFakeTaxiCarNames[index]

            val taxi = FakeTaxiModel.Taxi(
                coordinate,
                driverImage,
                driverName,
                driverRating,
                fleetType,
                heading,
                id,
                vehicleDistanceFromUser,
                vehicleEstimatedTimeOfArrival,
                vehicleModel
            )

            fakeTaxiList.add(taxi)
        }

        return fakeTaxiList
    }

    /**
     * Companion object, common to all instances of this class
     * Similar to static fields in Java
     */
    companion object {
        val TAG: String = ScopedFragment::class.java.simpleName
    }

    protected inline fun <reified V : ViewModel> bindViewModel() = lazy { ViewModelProviders.of(this, mViewModelFactory).get(V::class.java) }
}