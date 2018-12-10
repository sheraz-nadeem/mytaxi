package com.mytaxi.sheraz.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.NonNull
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

object SnackBarUtil {

    private val TAG: String = SnackBarUtil::class.java.simpleName

    /**
     * Snackbar with no action Button and Default Background (Default One)
     * @param context The calling context being used to instantiate the Snackbar.
     * @param message The message string that needs to be displayed inside
     * the snackbar
     * @param isLengthLong (true or false) to set snackbar display duration
     * true for LENGTH_LONG & false for LENGTH_SHORT
     */
    fun showSnackBar(@NonNull context: Context, message: String, isLengthLong: Boolean) {

        Log.d(TAG, "showSnackBar(): message: $message, isLengthLong: $isLengthLong")

        try {
            val snack = Snackbar.make(
                (context as Activity).findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_SHORT
            )

            if (isLengthLong)
                snack.duration = BaseTransientBottomBar.LENGTH_LONG

            val view = snack.view
            val tv = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            tv.setTextColor(Color.WHITE)

            snack.show()

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    /**
     * SnackBar with action Button and Default Background
     * @param context The calling context being used to instantiate the Snackbar.
     * @param message The message string that needs to be displayed inside
     * the snackbar
     * @param action String to show as action button text
     * @param listener View.OnClickListener to be called when action button is
     * clicked inside Snackbar
     */
    fun showSnackBarWithAction(context: Context, message: String, action: String, listener: View.OnClickListener) {

        Log.d(TAG, "showSnackBarWithAction(): message: $message, action: $action")

        try {
            val snack = Snackbar.make(
                (context as Activity).findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_INDEFINITE
            )

            snack.setAction(action, listener)

            val view = snack.view
            val tv = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            tv.setTextColor(Color.WHITE)

            snack.show()

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    /**
     * Snackbar with no action Button and Custom Background
     * @param context The calling context being used to instantiate the Snackbar.
     * @param message The message string that needs to be displayed inside
     * the snackbar
     * @param bgColor Integer representing the color resource id
     * @param isLengthLong (true or false) to set snackbar display duration
     * true for LENGTH_LONG & false for LENGTH_SHORT
     */
    fun showSnackbar(
        context: Context, message: String, bgColor: Int,
        isLengthLong: Boolean
    ) {

        Log.d(TAG, "showSnackBarWithAction(): message: $message, isLengthLong: $isLengthLong")

        try {
            val snack = Snackbar.make(
                (context as Activity).findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_SHORT
            )

            if (isLengthLong)
                snack.duration = BaseTransientBottomBar.LENGTH_LONG

            val view = snack.view
            view.setBackgroundColor(bgColor)
            val tv = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            tv.setTextColor(Color.WHITE)

            snack.show()

        } catch (ex: Exception) {

        }

    }

}
