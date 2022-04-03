package com.example.resumebuilder.utils

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.setErrorIfInvalid(errorMessage: String) {
    val text = this.editText?.text
    if (text.isNullOrBlank()) {

        this.error = errorMessage

    } else {

        this.error = null

    }
}

fun TextInputLayout.getText(): String {

    val text = this.editText?.text
    return text?.toString() ?: ""

}