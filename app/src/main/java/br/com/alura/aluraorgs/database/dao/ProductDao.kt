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

    @Query("SELECT * FROM Product order by name asc")
    fun getOrderByNameAsc(): List<Product>

    @Query("SELECT * FROM Product order by name desc")
    fun getOrderByNameDesc(): List<Product>

    @Query("SELECT * FROM Product order by value asc ")
    fun getOrderByValueAsc(): List<Product>

    @Query("SELECT * FROM Product order by value desc ")
    fun getOrderByValueDesc(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg product: Product)

    @Delete
    fun delete(vararg product: Product)
}