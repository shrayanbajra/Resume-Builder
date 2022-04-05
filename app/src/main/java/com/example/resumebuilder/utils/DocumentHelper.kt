package com.example.resumebuilder.utils

import android.content.Intent

object DocumentHelper {

    fun getIntentForCreatingPdf(fileName: String): Intent {

        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.type = "application/pdf"
        intent.putExtra(Intent.EXTRA_TITLE, "$fileName.pdf")
        return intent

    }

}