package com.example.supermarketapp.dataentities
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query



    @Dao
    interface ProductDao {
        //Query for inserting Products
        @Insert
        suspend fun insert(product: Product)

        //Query for Displaying Products
        @Query("SELECT * FROM product_table")
        suspend fun getAll() : List<Product>

        //Query for filtering Products
        @Query("SELECT * FROM product_table WHERE category = :category")
        suspend fun filter(category: String) : List<Product>

        @Query("DELETE FROM product_table")
        suspend fun deleteAll()

    }

