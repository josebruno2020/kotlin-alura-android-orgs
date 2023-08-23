package br.com.alura.aluraorgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.aluraorgs.database.converter.Converters
import br.com.alura.aluraorgs.database.dao.ProductDao
import br.com.alura.aluraorgs.model.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        private lateinit var db: ProductDatabase
        fun instance(context: Context): ProductDatabase {
            if (::db.isInitialized) return db
            return Room.databaseBuilder(
                context,
                ProductDatabase::class.java,
                "orgs.db"
            ).build().also {
                db = it
            }
        }
    }
}