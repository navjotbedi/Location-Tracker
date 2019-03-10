package com.groundtruth.location.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import com.google.android.material.snackbar.Snackbar


class Utils {
    companion object {
        fun showSnackbar(text: String, container: View) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show()
        }

        fun openWebPage(url: String, context: Context) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(browserIntent)
        }
    }
}