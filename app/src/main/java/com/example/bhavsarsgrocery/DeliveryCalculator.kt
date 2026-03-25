package com.example.bhavsarsgrocery
import android.location.Location

// 1. Define the Result types OUTSIDE the class so they are easy to find
sealed class DeliveryResult {
    data class Success(val charge: Double) : DeliveryResult()
    data class Error(val message: String) : DeliveryResult()
}

// 2. Use a 'singleton' object directly
object DeliveryCalculator {

    // Constants for your village rules
    const val MIN_ORDER_FOR_DELIVERY = 500.0
    const val FREE_DELIVERY_DISTANCE = 3.0
    const val CHARGE_PER_KM = 10.0

    fun calculateTotal(
        orderAmount: Double,
        distance: Double,
        isDelivery: Boolean
    ): DeliveryResult {
        // Rule: Collection (Pick-up) is always free
        if (!isDelivery) return DeliveryResult.Success(0.0)

        // Rule 1: Minimum ₹500 for delivery
        if (orderAmount < MIN_ORDER_FOR_DELIVERY) {
            return DeliveryResult.Error("Delivery requires a minimum order of ₹500")
        }

        // Rule 2: Distance calculation
        val deliveryCharge = if (distance <= FREE_DELIVERY_DISTANCE) {
            0.0 // Free under 3km
        } else {
            // Your rule: ₹10 per km for the total distance if over 3km
            distance * CHARGE_PER_KM
        }

        return DeliveryResult.Success(deliveryCharge)
    }
    fun calculateGpsDistance(shopLat: Double, shopLng: Double, customerLat: Double, customerLng: Double): Double {
        val shopLocation = Location("Shop").apply {
            latitude = shopLat
            longitude = shopLng
        }

        val customerLocation = Location("Customer").apply {
            latitude = customerLat
            longitude = customerLng
        }

        // Returns distance in meters, so we divide by 1000 for Kilometers
        val distanceInMeters = shopLocation.distanceTo(customerLocation)
        return (distanceInMeters / 1000).toDouble()
    }
}
