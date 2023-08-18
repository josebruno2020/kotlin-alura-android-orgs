package br.com.alura.aluraorgs.database.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

class Converters {
    @TypeConverter
    fun fromDouble(value: Double?): BigDecimal {
        return value?.let {
            BigDecimal(value.toString())
        } ?: BigDecimal(0)
    }

    @TypeConverter
    fun bigDecimalToDouble(value: BigDecimal?): Double? {
        return value?.let {
            it.toDouble()
        }
    }
}