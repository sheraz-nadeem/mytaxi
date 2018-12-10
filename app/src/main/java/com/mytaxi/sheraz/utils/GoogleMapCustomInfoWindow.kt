package com.mytaxi.sheraz.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.mytaxi.sheraz.R
import com.mytaxi.sheraz.ui.modules.home.fakemodel.FakeTaxiModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_home_grid.view.*
import java.lang.Exception


class GoogleMapCustomInfoWindow(
    private val mContext: Context
) : GoogleMap.InfoWindowAdapter {

    private lateinit var mMarker: Marker

    override fun getInfoContents(marker: Marker?): View {

        Log.d(TAG, "getInfoContents(): ")
        val itemView = (mContext as Activity).layoutInflater.inflate(R.layout.item_home_grid, null)
        mMarker = marker!!
        val model: FakeTaxiModel.Taxi = mMarker.tag as FakeTaxiModel.Taxi

        setUpViews(model, itemView)

        return itemView
    }

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }

    private fun setUpViews(itemContentModel: FakeTaxiModel.Taxi, itemView: View) {

        Log.d(
            TAG,
            "setUpViews(): driverName: ${itemContentModel.driverName}, driverImage: ${itemContentModel.driverImage}"
        )

        Picasso.get().load(itemContentModel.driverImage)
            .noFade()
            .placeholder(R.drawable.ic_driver)
            .into(itemView.ivProfileImage, object : Callback {
                override fun onSuccess() {
                    Log.v(TAG, "Picasso.onSuccess(): Marker is ready to be displayed...\n" +
                            "driverName: ${itemContentModel.driverName}, driverImage: ${itemContentModel.driverImage}")
//                    if (mMarker.isInfoWindowShown) mMarker.showInfoWindow()
                }

                override fun onError(e: Exception?) {
                    Log.e(TAG, "Picasso.onError(): image failed to load.", e)
                }
            })

        itemView.tvDriverName.text = itemContentModel.driverName
        itemView.tvEstimatedTimeOfArrival.text = itemContentModel.vehicleEstimatedTimeOfArrival
        itemView.tvEstimatedDistanceValue.text = itemContentModel.vehicleDistanceFromUser
        itemView.tvVehicleModelValue.text = itemContentModel.vehicleModel
        itemView.tvVehicleIdValue.text = itemContentModel.id.toString()

        when (itemContentModel.fleetType) {
            "POOLING" -> {
                itemView.tvAvailabilityValue.text = "AVAILABLE"
                itemView.tvAvailabilityValue.setTextColor(mContext.resources.getColor(android.R.color.holo_green_light))
            }
            "TAXI" -> {
                itemView.tvAvailabilityValue.text = "UNAVAILABLE"
                itemView.tvAvailabilityValue.setTextColor(mContext.resources.getColor(android.R.color.holo_red_light))
            }
        }

        when (itemContentModel.driverRating) {
            1 -> {
                itemView.ivRatingStar1.visibility = View.VISIBLE
                itemView.ivRatingStar2.visibility = View.GONE
                itemView.ivRatingStar3.visibility = View.GONE
                itemView.ivRatingStar4.visibility = View.GONE
                itemView.ivRatingStar5.visibility = View.GONE
            }
            2 -> {
                itemView.ivRatingStar1.visibility = View.VISIBLE
                itemView.ivRatingStar2.visibility = View.VISIBLE
                itemView.ivRatingStar3.visibility = View.GONE
                itemView.ivRatingStar4.visibility = View.GONE
                itemView.ivRatingStar5.visibility = View.GONE
            }
            3 -> {
                itemView.ivRatingStar1.visibility = View.VISIBLE
                itemView.ivRatingStar2.visibility = View.VISIBLE
                itemView.ivRatingStar3.visibility = View.VISIBLE
                itemView.ivRatingStar4.visibility = View.GONE
                itemView.ivRatingStar5.visibility = View.GONE
            }
            4 -> {
                itemView.ivRatingStar1.visibility = View.VISIBLE
                itemView.ivRatingStar2.visibility = View.VISIBLE
                itemView.ivRatingStar3.visibility = View.VISIBLE
                itemView.ivRatingStar4.visibility = View.VISIBLE
                itemView.ivRatingStar5.visibility = View.GONE
            }
            5 -> {
                itemView.ivRatingStar1.visibility = View.VISIBLE
                itemView.ivRatingStar2.visibility = View.VISIBLE
                itemView.ivRatingStar3.visibility = View.VISIBLE
                itemView.ivRatingStar4.visibility = View.VISIBLE
                itemView.ivRatingStar5.visibility = View.VISIBLE
            }
        }
    }


    /**
     * Companion object, common to all instances of this class
     * Similar to static fields in Java
     */
    companion object {
        val TAG: String = GoogleMapCustomInfoWindow::class.java.simpleName
    }

}