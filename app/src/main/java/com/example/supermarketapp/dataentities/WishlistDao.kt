package com.example.supermarketapp.dataentities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WishlistDao {

    @Insert
    suspend fun insert(item: WishlistItem)

    @Query("SELECT * FROM wishlist_table")
    suspend fun getAll(): List<WishlistItem>

    @Query("DELETE FROM wishlist_table WHERE id = :id")
    suspend fun deleteById(id: Int)
}
