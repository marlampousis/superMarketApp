package com.example.supermarketapp.dataentities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PurchaseHistoryDao {

    @Insert
    suspend fun insert(purchase: PurchaseHistory)

    @Query("SELECT * FROM purchase_history_table ORDER BY date DESC")
    suspend fun getAll(): List<PurchaseHistory>

    @Query("DELETE FROM purchase_history_table WHERE id = :id")
    suspend fun deleteById(id: Int)
}