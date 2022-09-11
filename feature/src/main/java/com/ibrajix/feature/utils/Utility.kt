package com.ibrajix.feature.utils

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

object Utility {

    fun Fragment.showSnackBar(
        message: String,
        duration: Int = Snackbar.LENGTH_LONG,
        view: View = requireView()
    ) {
        Snackbar.make(view, message, duration).show()
    }

}