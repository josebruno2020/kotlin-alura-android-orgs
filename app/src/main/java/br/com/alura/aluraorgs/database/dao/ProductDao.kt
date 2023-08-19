package br.com.alura.aluraorgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alura.aluraorgs.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product order by id desc")
    fun searchAll(): List<Product>

    @Query("SELECT * FROM PRODUCT where id = :id limit 1")
    fun getById(id: Long): Product?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg product: Product)

    @Delete
    fun delete(vararg product: Product)
}