package br.com.alura.aluraorgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import br.com.alura.aluraorgs.databinding.ImageFormBinding
import br.com.alura.aluraorgs.extensions.tryLoadImage

class FormImageDialog(
    private val context: Context
) {

    fun showDialog(
        defaultUrl: String? = null,
        loadSuccess: (image: String) -> Unit
    ) {
        val binding = ImageFormBinding.inflate(
            LayoutInflater.from(context)
        ).apply {
            defaultUrl?.let {
                previewImage.tryLoadImage(it)
                previewUrl.setText(it)
            }
        }


        binding.previewButton.setOnClickListener {
            val imageUrl = binding.previewUrl.text.toString()
            binding.previewImage.tryLoadImage(imageUrl)
        }
        AlertDialog.Builder(context)
            .setView(binding.root)
            .setPositiveButton("Confirmar") { _, _ ->
                val imageUrl = binding.previewUrl.text.toString()
                loadSuccess(imageUrl)

            }
            .setNegativeButton("Cancelar") { _, _ -> }
            .show()
    }
}