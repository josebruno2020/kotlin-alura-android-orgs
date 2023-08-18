package br.com.alura.aluraorgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.alura.aluraorgs.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product order by id desc")
    fun searchAll(): List<Product>

    @Insert
    fun insert(vararg product: Product)

    @Update
    fun update(vararg product: Product)

    @Delete
    fun delete(vararg product: Product)
}