package com.ibrajix.core.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import com.ibrajix.core.BuildConfig

object Utility {

    //make status bar color white
    fun Activity.whiteStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        window.statusBarColor = Color.WHITE
    }

    const val SPLASH_SCREEN_TIME = 3500L

    /**
     * ENDPOINTS
     */
    const val API_KEY = "HEello"

}