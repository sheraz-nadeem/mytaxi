package com.mytaxi.sheraz.ui.modules.home.view

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.idling.CountingIdlingResource
import com.mytaxi.sheraz.data.db.entity.MyTaxiEntry

import com.mytaxi.sheraz.databinding.HomeFragmentBinding
import com.mytaxi.sheraz.internal.NoConnectivityException
import com.mytaxi.sheraz.internal.bindViewModel
import com.mytaxi.sheraz.ui.modules.base.ScopedFragment
import com.mytaxi.sheraz.ui.modules.home.adapters.HomeGridAdapter
import com.mytaxi.sheraz.ui.modules.home.fakemodel.FakeTaxiModel
import com.mytaxi.sheraz.ui.modules.home.viewmodel.HomeViewModel
import com.mytaxi.sheraz.utils.RuntimePermissionsHandler
import com.mytaxi.sheraz.utils.RuntimePermissionsHandler.REQUEST_LOCATION
import com.mytaxi.sheraz.utils.SnackBarUtil
import com.mytaxi.sheraz.utils.SpacesItemDecoration
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception

const val pOneLat: String = "53.694865"
const val pOneLng: String = "9.757589"
const val pTwoLat: String = "53.394655"
const val pTwoLng: String = "10.099891"

class HomeFragment : ScopedFragment(), SwipeRefreshLayout.OnRefreshListener {


    private val mViewModel: HomeViewModel by bindViewModel(mViewModelFactory)

    // Binding
    private lateinit var mBinding: HomeFragmentBinding
    private var mSelectedModel: FakeTaxiModel.Taxi? = null
    private var mIsInitialLoadingDone = false


    /**
     * Fragment Lifecycle methods
     */
    override fun onAttach(context: Context?) {
        Log.d(TAG, "onAttach(): ")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate(): ")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "onCreateView(): ")

        // Use Generated binding class HomeFragmentBinding.inflate() method, not DataBindingUtil.inflate() method,
        // as we already know which layout is going to be inflated
        mBinding = HomeFragmentBinding.inflate(inflater, container, false)
        return mBinding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated(): ")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        Log.d(TAG, "onActivityCreated(): ")
        super.onActivityCreated(savedInstanceState)

    }

    override fun onStart() {
        Log.d(TAG, "onStart(): ")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume(): ")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause(): ")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop(): ")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView(): ")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy(): ")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach(): ")
        super.onDetach()
    }

    /**
     * Initialization methods
     */
    override fun initUI() {
        Log.d(TAG, "initUI(): ")

        rvTaxiList.setHasFixedSize(true)
        rvTaxiList.layoutManager = GridLayoutManager(context, 2)
        rvTaxiList.addItemDecoration(SpacesItemDecoration(10))

        swipeRefreshLayout.setOnRefreshListener(this)

        fabTrackData.setOnClickListener {

            Log.d(TAG, "fabTrackData.onClick(): ")
            showMapFragment()

        }
    }

    override fun onRefresh() {

        Log.d(TAG, "onRefresh(): ")

        // Increment Idling resource telling Espresso that we are busy in
        // some background task
        if (getIdlingResource().isIdleNow) {
            getIdlingResource().increment()
        }

        swipeRefreshLayout.isRefreshing = true
        mViewModel.getMyTaxis(pOneLat, pOneLng, pTwoLat, pTwoLng)

    }

    /**
     * Coroutine methods that perform data fetching and subscribe
     * observer for changes
     */

    override fun fetchMyTaxiListAndObserve() = launch(context = coroutineContext) {

        Log.d(TAG, "fetchMyTaxiListAndObserve(): currentThreadName ${Thread.currentThread().name}")

        try {

            // Increment Idling resource telling Espresso that we are busy in
            // some background task
            if (getIdlingResource().isIdleNow) {
                getIdlingResource().increment()
            }


            val myTaxiListLiveData = mViewModel.getMyAllTaxisLiveData()
            subscribeUI(myTaxiListLiveData)

            // Initiate fetch data from the API when data population from DB is done
            if (!mIsInitialLoadingDone) {
                mIsInitialLoadingDone = true
                onRefresh()
            }

        } catch (e: Exception) {

            Log.e(TAG, "fetchMyTaxiListAndObserve(): Exception occurred, Error => ${e.message}", e)

            if (e is NoConnectivityException) {

                if (isAdded && context != null) {
                    SnackBarUtil.showSnackBar(context!!, "Internet connectivity problem", true)
                }
            }
        }
    }

    private fun subscribeUI(liveData: LiveData<List<MyTaxiEntry>>) {

        Log.d(TAG, "subscribeUI(): ")

        liveData.observe(this@HomeFragment, Observer {
            if (it == null || it.isEmpty()) return@Observer

            // Decrement Idling resource telling Espresso that we are done doing
            // some background task
            if (!getIdlingResource().isIdleNow) {
                getIdlingResource().decrement()
            }
            Log.v(TAG, "subscribeUI(): getIdlingResource().isIdleNow: ${getIdlingResource().isIdleNow}")

            launch (context = coroutineContext) {
                // Offload data generation routine off of main thread
                val fakeData = async(Dispatchers.IO) { generateFakeTaxiData(it) }.await()
                if (isAdded && context != null) {
                    setUpGridAdapter(fakeData)
                }
            }
        })

    }

    /**
     * RecyclerView methods
     */

    private fun setUpGridAdapter(list: List<FakeTaxiModel.Taxi>) {

        Log.d(TAG, "setUpGridAdapter(): list.size: ${list.size}")

        val homeGridAdapter = HomeGridAdapter(context!!, View.OnClickListener {
            Log.d(TAG, "HomeGridAdapter.OnClick(): ")

            if (it == null) {
                mSelectedModel = null
            } else {
                mSelectedModel = it.tag as FakeTaxiModel.Taxi
            }

            toggleFabIfNecessary()

        }, list)
        rvTaxiList.adapter = homeGridAdapter
        swipeRefreshLayout.isRefreshing = false
    }

    private fun toggleFabIfNecessary() {
        Log.d(
            TAG,
            "toggleFabIfNecessary(): taxi.driverName: ${mSelectedModel?.driverName}, taxi.vehicleModel: ${mSelectedModel?.vehicleModel}"
        )

        if (mSelectedModel == null) fabTrackData.hide()
        else fabTrackData.show()
    }


    /**
     * Location Permission methods
     */

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_LOCATION) {

            if (permissions.size == 1 &&
                permissions[0] == ACCESS_FINE_LOCATION &&
                grantResults[0] == PERMISSION_GRANTED
            ) {
                showMapFragment()
            } else {
                SnackBarUtil.showSnackBar(context!!, "App needs some permissions to work properly", true)
            }
        }
    }

    /**
     * Transition to MapFragment methods
     */
    private fun showMapFragment() {
        Log.d(TAG, "showMapFragment(): ")

        if (isAdded && context != null) {
            if (!RuntimePermissionsHandler.isLocationPermissionGranted(context!!)) {
                RuntimePermissionsHandler.requestLocationPermissionWithFragment(this)
            } else {
                Log.i(
                    TAG,
                    "showMapFragment(): Location permission is already granted, proceed with fragment transaction."
                )

                if (activity is HomeActivity) {
                    (activity as HomeActivity).loadMapFragment(mSelectedModel!!)
                }
            }
        }
    }

    /**
     * Companion object, common to all instances of this class
     * Similar to static fields in Java
     */
    companion object {
        val TAG: String = HomeFragment::class.java.simpleName

        fun newInstance() = HomeFragment()
    }

    /**
     * Get IdlingResource from HomeActivity
     */
    private fun getIdlingResource(): CountingIdlingResource {
        return (activity as HomeActivity).getIdlingResourceInTest()
    }
}
