package com.example.supermarketapp.dataentities
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ShoppingListDao {

    @Insert
    suspend fun insert(item: ShoppingListItem)

    @Query("SELECT * FROM shopping_list_table")
    suspend fun getAll(): List<ShoppingListItem>

    @Query("DELETE FROM shopping_list_table WHERE id = :id")
    suspend fun deleteById(id: Int)
}