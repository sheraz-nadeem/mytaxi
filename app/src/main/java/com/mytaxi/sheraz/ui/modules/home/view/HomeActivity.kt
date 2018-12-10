package com.mytaxi.sheraz.ui.modules.home.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mytaxi.sheraz.R
import com.mytaxi.sheraz.databinding.ActivityHomeBinding
import com.mytaxi.sheraz.ui.modules.home.fakemodel.FakeTaxiModel
import android.view.MenuItem
import androidx.test.espresso.idling.CountingIdlingResource



class HomeActivity : AppCompatActivity() {

    // Binding
    private var mBinding: ActivityHomeBinding? = null

    var mIdlingResource = CountingIdlingResource("busy")


    /**
     * Activity Lifecycle Methods
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        if (savedInstanceState == null) {
            loadHomeFragment()
        }
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

    override fun onDestroy() {
        Log.d(TAG, "onDestroy(): ")
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                val fragment = supportFragmentManager.findFragmentByTag(MapFragment.TAG)
                if (fragment is MapFragment) {
                    Log.d(TAG, "onOptionsItemSelected(): home button in action bar is clicked," +
                            "MapFragment found, now load home fragment.")

                    loadHomeFragment()
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    /**
     * Other Activity Methods
     */
    private fun loadHomeFragment() {

        Log.d(TAG, "loadHomeFragment(): ")
        val ft = supportFragmentManager.beginTransaction()
        val homeFragment = HomeFragment.newInstance()
        ft.replace(R.id.mainContainer, homeFragment, HomeFragment.TAG)
        ft.commit()

        supportActionBar?.setHomeButtonEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }

    fun loadMapFragment(selectedTaxi: FakeTaxiModel.Taxi) {

        Log.d(TAG, "loadMapFragment(): ")

        val ft = supportFragmentManager.beginTransaction()
        val mapFragment = MapFragment.newInstance()

        val args = Bundle()
        args.putParcelable("selected_taxi", selectedTaxi)
        mapFragment.arguments = args

        ft.replace(R.id.mainContainer, mapFragment, MapFragment.TAG)
        ft.commit()

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    /**
     * Companion object, common to all instances of this class
     * Similar to static fields in Java
     */
    companion object {
        private val TAG: String = HomeActivity::class.java.simpleName
    }


    /**
     * Methods used for testing purposes
     */
    fun getIdlingResourceInTest(): CountingIdlingResource {
        return mIdlingResource
    }
}
