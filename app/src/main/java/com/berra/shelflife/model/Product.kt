package com.berra.shelflife.model

data class Product(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val category: ProductCategory = ProductCategory.FOOD,
    val location: ProductLocation = ProductLocation.PANTRY,
    val quantity: Double = 0.0,
    val unit: String = "",
    val purchaseDate: Long? = null,
    val expiryDate: Long = 0L,
    val openedDate: Long? = null,
    val notes: String = "",
    val imageUrl: String? = null,
    val barcode: String? = null,
    val createdAt: Long = 0L
)