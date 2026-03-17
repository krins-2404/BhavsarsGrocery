package com.example.bhavsarsgrocery

// The basic structure of a grocery item
data class GroceryItem(
    val id: String = "",
    val name: String,
    val category: String, // e.g., "Daily Grocery", "Dairy", "Festival Items"
    val price: Double,
    val isAvailable: Boolean = true,
    val stockQty: Int = 0
)

// The structure of an order with your 3km/500rs rules
data class Order(
    val id: String = "",
    val customerName: String,
    val items: List<GroceryItem>,
    val totalAmount: Double,
    val distanceKm: Double,
    val isDelivery: Boolean, // True for Delivery, False for Collection
    val paymentStatus: String = "Pending" // Pending, Paid, COD_Requested
)
