package com.example.bhavsarsgrocery

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CartScreen(orderAmount: Double, userDistance: Double) {
    // State to hold the customer's typed address
    var deliveryAddress by remember { mutableStateOf("") }

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
                val charge = deliveryResult.charge
                val totalToPay = orderAmount + charge

                Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Delivery Charge (${"%.1f".format(userDistance)}km):")

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

        Spacer(modifier = Modifier.weight(1f).height(24.dp))

        // --- NEW: MANUAL ADDRESS ENTRY ---
        Text("Delivery Details", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = deliveryAddress,
            onValueChange = { deliveryAddress = it },
            label = { Text("House No. / Landmark / Street") },
            placeholder = { Text("e.g., Near the big banyan tree") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )
        Text(
            text = "Distance is calculated automatically using your phone's location.",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
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
@Composable
fun AddressSelection(onDistanceCalculated: (Double) -> Unit) {
    var address by remember { mutableStateOf("") }
    var manualKm by remember { mutableStateOf("") }

    Column {
        Text("Delivery Address", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Enter Landmark/House Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Or enter approximate distance (KM):", style = MaterialTheme.typography.bodySmall)
        OutlinedTextField(
            value = manualKm,
            onValueChange = {
                manualKm = it
                val km = it.toDoubleOrNull() ?: 0.0
                onDistanceCalculated(km)
            },
            label = { Text("KM from Bhavsar's Shop") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
