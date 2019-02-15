package com.mytaxi.sheraz.internal

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 * AppCompatActivity Extension functions
 */
fun AppCompatActivity.isPermissionGranted(permission: String) =
    ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun AppCompatActivity.shouldShowPermissionRationale(permission: String) =
    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

fun AppCompatActivity.requestPermission(permission: String, requestId: Int) =
    ActivityCompat.requestPermissions(this, arrayOf(permission), requestId)

fun AppCompatActivity.batchRequestPermissions(permissions: Array<String>, requestId: Int) =
    ActivityCompat.requestPermissions(this, permissions, requestId)


/**
 * Fragment Extension functions
 */
inline fun <reified V : ViewModel> Fragment.bindViewModel(viewModelFactory: ViewModelProvider.Factory) = lazy { ViewModelProviders.of(this, viewModelFactory).get(V::class.java) }
