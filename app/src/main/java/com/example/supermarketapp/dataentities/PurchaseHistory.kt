package com.example.supermarketapp.dataentities
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchase_history_table")
data class PurchaseHistory(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val date: String,
    val totalCost: Double
)