package com.example.omdbdemoapp.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

object DialogUtil {

    fun showSnackBar(view: View, message: String) {
        val snackBar: Snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        snackBar.show()
    }
}