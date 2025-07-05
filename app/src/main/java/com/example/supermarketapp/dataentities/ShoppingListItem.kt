package com.example.supermarketapp.dataentities

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "shopping_list_table")

data class ShoppingListItem (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val productId: Int,
    val quantity: Int
)