package com.example.bhavsarsgrocery

class DeliveryCalculator {
    // This makes the constants and functions accessible
    // via the class name: DeliveryCalculator.calculateTotal(...)

    object companion {
        const val MIN_ORDER_FOR_DELIVERY = 500.0
        const val FREE_DELIVERY_DISTANCE = 3.0
        const val CHARGE_PER_KM = 10.0

        fun calculateTotal(
            orderAmount: Double,
            distance: Double,
            isDelivery: Boolean
        ): DeliveryResult {
            if (!isDelivery) return DeliveryResult.Success(0.0) // Collection is always free

            // Rule 1: Minimum 500 Rupees for delivery
            if (orderAmount < MIN_ORDER_FOR_DELIVERY) {
                return DeliveryResult.Error("Delivery requires a minimum order of ₹500")
            }

            // Rule 2: Distance calculation (Free < 3km, else 10/km)
            val deliveryCharge = if (distance <= FREE_DELIVERY_DISTANCE) {
                0.0
            } else {
                // Charges for every KM above 3, or total?
                // Usually, it's total distance * 10 if above 3km
                distance * CHARGE_PER_KM
            }

            return DeliveryResult.Success(deliveryCharge)
        }
    }

    sealed class DeliveryResult {
        data class Success(val charge: Double) : DeliveryResult()
        data class Error(val message: String) : DeliveryResult()
    }
}
