package com.mytaxi.sheraz.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import java.lang.System.out


object RuntimePermissionsHandler {

    private val TAG: String = RuntimePermissionsHandler::class.java.simpleName

    /**
     * Request ID for handling Location permission
     */
    const val REQUEST_LOCATION = 0


    fun isLocationPermissionGranted(context: Context): Boolean {

        Log.d(TAG, "isLocationPermissionGranted(): ")
        val isAccessFineLocationPermissionGranted = ContextCompat.checkSelfPermission(context.applicationContext, ACCESS_FINE_LOCATION)
        val isGranted = isAccessFineLocationPermissionGranted == PackageManager.PERMISSION_GRANTED
        Log.v(TAG, "isLocationPermissionGranted(): isGranted: $isGranted")

        return isGranted

    }

    /**
     * Requests the Location permission.
     * If the permission has been denied previously, a SnackBar will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    fun requestLocationPermissionWithFragment(fragment: Fragment) {

        Log.d(TAG, "requestLocationPermission(): LOCATION permission has NOT been granted. Requesting permission.")

        if (fragment.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {

            Log.i(TAG, "requestLocationPermission(): Displaying location permission rationale to provide additional context.")
            SnackBarUtil.showSnackBarWithAction(fragment.context!!, "App needs to access your location for some of it's features to work properly", "OK", View.OnClickListener {
                fragment.requestPermissions(Array(1){Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION)
            })

        } else {

            // Request location permission as it's not granted yet
            fragment.requestPermissions(Array(1){Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION)

        }
    }
}