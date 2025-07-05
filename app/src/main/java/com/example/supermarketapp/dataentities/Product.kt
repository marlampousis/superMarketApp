package com.example.supermarketapp.dataentities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product (
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val name: String?,
    val category: String?,
    val description: String?,
    val price: Double?,
    val quantity: Int?,
    val imagePath: String?,
    val unit : String?,
    val isOnOffer: Boolean?,
    val offerType: String?,
    val isOnSale: Boolean?,
    val discountPercentage: Double?,
    val discountedPrice: Double?
)