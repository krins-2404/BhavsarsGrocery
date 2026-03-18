package com.example.bhavsarsgrocery

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CartScreen(orderAmount: Double, userDistance: Double) {
    // Check our village rules using the Calculator
    val deliveryResult = DeliveryCalculator.calculateTotal(
        orderAmount = orderAmount,
        distance = userDistance,
        isDelivery = true
    )

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Your Order Summary", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(20.dp))

        // Show Order Items Total
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Items Total:")
            Text("₹$orderAmount")
        }

        // Show Delivery Logic
        when (deliveryResult) {
            is DeliveryResult.Success -> {
                Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Delivery Charge (${userDistance}km):")
                    Text(if (deliveryResult.charge == 0.0) "FREE" else "₹${deliveryResult.charge}")
                }

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                Text(
                    "Total to Pay: ₹${orderAmount + deliveryResult.charge}",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF388E3C) // Green color for success
                )
            }
            is DeliveryResult.Error -> {
                // If under 500 Rupees, show a warning
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = deliveryResult.message,
                        modifier = Modifier.padding(16.dp),
                        color = Color.Red
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Place Order Button (only enabled if delivery is valid)
        Button(
            onClick = { /* Navigate to Payment Screen */ },
            enabled = deliveryResult is DeliveryResult.Success,
            modifier = Modifier.fillMaxWidth().height(55.dp)
        ) {
            Text("Proceed to Payment")
        }
    }
}
