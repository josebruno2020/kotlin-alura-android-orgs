package br.com.alura.aluraorgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.alura.aluraorgs.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product order by id desc")
    fun searchAll(): Flow<List<Product>>

    @Query("SELECT * FROM PRODUCT where id = :id limit 1")
    fun getById(id: Long): Flow<Product?>

    @Query("SELECT * FROM Product order by name asc")
    fun getOrderByNameAsc(): Flow<List<Product>>

    @Query("SELECT * FROM Product order by name desc")
    fun getOrderByNameDesc(): Flow<List<Product>>

    @Query("SELECT * FROM Product order by value asc ")
    fun getOrderByValueAsc(): Flow<List<Product>>

    @Query("SELECT * FROM Product order by value desc ")
    fun getOrderByValueDesc(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg product: Product)

    @Delete
    suspend fun delete(vararg product: Product)
}