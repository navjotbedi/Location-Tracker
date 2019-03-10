package com.groundtruth.location.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

class Utils {
    companion object {
        fun showSnackbar(text: String, container: View) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show()
        }
    }
}