package com.example.resumebuilder.utils

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, length).show()
}

fun Fragment.showToast(@StringRes message: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, length).show()
}