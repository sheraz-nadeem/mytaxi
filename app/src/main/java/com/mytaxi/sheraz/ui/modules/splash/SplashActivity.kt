package com.mytaxi.sheraz.ui.modules.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mytaxi.sheraz.R
import com.mytaxi.sheraz.databinding.ActivitySplashBinding
import com.mytaxi.sheraz.ui.modules.home.view.HomeActivity

class SplashActivity : AppCompatActivity(), AnimationListener {

    // Binding
    private var mBinding: ActivitySplashBinding? = null

    /**
     * Activity Lifecycle Methods
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d(TAG, "onCreate(): ")
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
    }

    override fun onStart() {
        Log.d(TAG, "onStart(): ")
        super.onStart()
        startBounceAnimation()
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


    /**
     * Animation Listener methods
     */
    override fun onAnimationStart(animation: Animation?) {
        Log.d(TAG, "onAnimationStart(): ")
    }

    override fun onAnimationEnd(animation: Animation?) {
        Log.d(TAG, "onAnimationEnd(): ")
        launchMainActivity()
    }

    override fun onAnimationRepeat(animation: Animation?) {
        Log.d(TAG, "onAnimationRepeat(): ")
    }

    /**
     * Method to start bounce animation on ivTaxi image view
     */
    private fun startBounceAnimation() {
        Log.d(TAG, "startBounceAnimation(): ")
        val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.bouncy)
        animation.setAnimationListener(this)
        mBinding?.ivTaxi?.startAnimation(animation)
    }

    /**
     * Intent to Launch HomeActivity
     */
    private fun launchMainActivity() {
        Log.d(TAG, "launchMainActivity(): ")
        val intent = Intent(this@SplashActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Companion object, common to all instances of this class
     * Similar to static fields in Java
     */
    companion object {
        private val TAG = SplashActivity::class.java.simpleName
    }
}
