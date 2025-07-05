package com.example.supermarketapp.dataentities
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlist_table")
data class WishlistItem(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val productId: Int
)
