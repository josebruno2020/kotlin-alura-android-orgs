package br.com.alura.aluraorgs.ui.toast

import android.content.Context
import android.widget.Toast

class ToastMessage(
    private val context: Context
) {
    fun showToastMessage(message: String) {
        Toast
            .makeText(context, message, Toast.LENGTH_LONG)
            .show()
    }
}