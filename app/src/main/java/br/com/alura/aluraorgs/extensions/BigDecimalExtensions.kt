package br.com.alura.aluraorgs.extensions

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

fun BigDecimal.formatBrValue(): String {
    val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
    return currencyFormat.format(this)
}