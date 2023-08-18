package br.com.alura.aluraorgs.ui.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog

class ConfirmDialog(
    private val context: Context
) {
    fun showDialog(
        message: String = "Confirma a ação desejada?",
        onClickSuccess: () -> Unit = {},
        onClickCancel: () -> Unit = {}
    ) {
        AlertDialog.Builder(context)
            .setTitle("Atenção")
            .setMessage(message)
            .setPositiveButton("Confirmar") { _, _ -> onClickSuccess() }
            .setNegativeButton("Cancelar") { _, _ -> onClickCancel() }
            .show()
    }
}