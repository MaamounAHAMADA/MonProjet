package com.example.projet_esimed_mobile

import android.content.Context
import androidx.appcompat.app.AlertDialog

object Tools {
    fun displayError(context: Context, messageId: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(messageId)
            .setTitle("Error")
            .setPositiveButton(android.R.string.ok, null)
        val dialog = builder.create()
        dialog.show()
    }
}