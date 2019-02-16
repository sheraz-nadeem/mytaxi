package com.mytaxi.sheraz.ui.modules.home.view

import android.content.Context
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.mytaxi.sheraz.databinding.FragmentMapBinding
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.mytaxi.sheraz.R
import com.mytaxi.sheraz.data.db.entity.MyTaxiEntry
import com.mytaxi.sheraz.internal.NoConnectivityException
import com.mytaxi.sheraz.internal.bindViewModel
import com.mytaxi.sheraz.ui.modules.base.ScopedFragment
import com.mytaxi.sheraz.ui.modules.home.fakemodel.FakeTaxiModel
import com.mytaxi.sheraz.ui.modules.home.viewmodel.HomeViewModel
import com.mytaxi.sheraz.utils.GoogleMapCustomInfoWindow
import com.mytaxi.sheraz.utils.SnackBarUtil
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception


class MapFragment : ScopedFragment(), OnMapReadyCallback {

    private val mCustomGoogleMapInfoWindow by lazy { return@lazy GoogleMapCustomInfoWindow(context!!) }
    private val mMapStyle by lazy { return@lazy MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style_retro) }
    private val mCustomMapPinBitmap by lazy { return@lazy AppCompatResources.getDrawable(context!!, R.drawable.ic_driver)!!.toBitmap() }

    private val mViewModel: HomeViewModel by bindViewModel(mViewModelFactory)

    // Binding
    private lateinit var mBinding: FragmentMapBinding
    private lateinit var mMap: GoogleMap
    private lateinit var mSelectedTaxiItem: FakeTaxiModel.Taxi


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

        if (arguments != null) {
            mSelectedTaxiItem = arguments!!.getParcelable("selected_taxi")!!
            Log.v(TAG, "onCreate(): selectedTaxiName: ${mSelectedTaxiItem.driverName}," +
                    "selectedTaxiModel: ${mSelectedTaxiItem.vehicleModel}")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "onCreateView(): ")

        // Use Generated binding class FragmentMapBinding.inflate() method, not DataBindingUtil.inflate() method,
        // as we already know which layout is going to be inflated
        mBinding = FragmentMapBinding.inflate(inflater, container, false)
        return mBinding.root

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
        mCustomMapPinBitmap.recycle()
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
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

    }

    /**
     * Map related methods
     */

    override fun onMapReady(map: GoogleMap) {

        Log.d(TAG, "onMapReady(): ")
        launch (context = coroutineContext) {
            mMap = map
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(mSelectedTaxiItem.coordinate.latitude, mSelectedTaxiItem.coordinate.longitude), 14f))
            setStyledMap()
            mMap.setInfoWindowAdapter(mCustomGoogleMapInfoWindow)
        }

    }

    private fun setStyledMap() {

        Log.d(TAG, "setStyledMap(): ")
        mMap.setMapStyle(mMapStyle)

    }

    private fun getMarkerOptionsForTaxi(taxi: FakeTaxiModel.Taxi): Marker {

        Log.d(TAG, "getMarkerOptionsForTaxi(): ")

        val markerOptions = MarkerOptions()
        markerOptions.position(LatLng(taxi.coordinate.latitude, taxi.coordinate.longitude))
            .title(taxi.driverName)
            .snippet("Here comes MyTaxi!!!  :)")
            .icon(BitmapDescriptorFactory.fromBitmap(mCustomMapPinBitmap))

        val marker = mMap.addMarker(markerOptions)
        marker.tag = taxi
        return marker

    }


    /**
     * Coroutine methods that perform data fetching and subscribe
     * observer for changes
     */

    override fun fetchMyTaxiListAndObserve() = launch (context = coroutineContext) {

        Log.d(TAG, "fetchMyTaxiListAndObserve(): currentThreadName ${Thread.currentThread().name}")

        try {

            val myTaxiListLiveData = mViewModel.getMyTaxisAvailableLiveData()
            subscribeUI(myTaxiListLiveData)

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

        liveData.observe(this@MapFragment, Observer {
            if (it == null || it.isEmpty()) return@Observer

            Log.v(TAG, "subscribeUI(): observer returned => $it")

            launch (context = coroutineContext) {

                val fakeData = async { generateFakeTaxiData(it) }.await()

                for (availableTaxi in fakeData) {

                    Log.v(TAG, "subscribeUI(): Adding marker, driverName: $availableTaxi")
                    val marker = getMarkerOptionsForTaxi(availableTaxi)
                }

                Log.v(TAG, "subscribeUI(): After adding all the markers, now adding selected taxi marker.")
                getMarkerOptionsForTaxi(mSelectedTaxiItem)
            }
        })

    }

    /**
     * Companion object, common to all instances of this class
     * Similar to static fields in Java
     */
    companion object {
        val TAG: String = MapFragment::class.java.simpleName

        fun newInstance() = MapFragment()

        private val HAMBURG = LatLng(53.551086, 9.993682)
    }
}