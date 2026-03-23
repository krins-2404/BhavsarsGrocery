package com.example.bhavsarsgrocery

data class GroceryItem(
    val id: String = "",
    val name: String,
    val category: String,
    val price: Double,
    val isAvailable: Boolean = true,
    val stockQty: Int = 0
)

data class Order(
    val id: String,
    val customerName: String,
    val customerPhone: String,
    val items: List<GroceryItem>,
    val totalAmount: Double,
    val status: String = "Pending",
    val distanceKm: Double,
    val isDelivery: Boolean,
    val paymentStatus: String = "Pending",
    val isCod: Boolean = true
)

data class WholesalerTransaction(
    val wholesalerName: String,
    val date: String,
    val type: String,           // "STOCK_ARRIVAL" or "PAYMENT_GIVEN"
    val paymentGivenTo: String, // Can be "N/A" for stock arrival
    val amount: Double,         // Bill amount or Payment amount
    val details: String,        // "10 Bags Sugar" or "Paid via Cash"
    val amountPaid: Double,
    val orderDetails: String,
    val deliveryStatus: String
)
