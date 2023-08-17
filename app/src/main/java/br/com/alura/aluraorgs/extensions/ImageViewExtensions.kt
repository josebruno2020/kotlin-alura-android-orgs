package br.com.alura.aluraorgs.extensions

import android.widget.ImageView
import br.com.alura.aluraorgs.R
import coil.load

fun ImageView.tryLoadImage(url: String? = null, fallbackImageDefault: Boolean = false) {
    load(url) {
        if (fallbackImageDefault) {
            fallback(R.drawable.imagem_padrao)
        } else {
            fallback(R.drawable.erro)
        }
        error(R.drawable.erro)
        placeholder(R.drawable.placeholder)
    }
}