package com.example.bhavsarsgrocery

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PaymentScreen(totalAmount: Double, onOrderPlaced: () -> Unit) {
    var selectedMethod by remember { mutableStateOf("") }
    var codNote by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Select Payment Method", style = MaterialTheme.typography.headlineSmall)
        Text("Total to Pay: ₹$totalAmount", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // Option 1: Online QR
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            RadioButton(selected = selectedMethod == "QR", onClick = { selectedMethod = "QR" })
            Text("Online Payment (Paytm/UPI QR)")
        }

        if (selectedMethod == "QR") {
            Card(modifier = Modifier.padding(16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("1. Scan Shop QR Code")
                    Text("2. Pay ₹$totalAmount")
                    Text("3. Keep screenshot for delivery person")
                    // Note: In a real app, you'd show an Image() here of your QR
                }
            }
        }

        // Option 2: COD with Approval
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            RadioButton(selected = selectedMethod == "COD", onClick = { selectedMethod = "COD" })
            Text("Cash on Delivery (Needs Approval)")
        }

        if (selectedMethod == "COD") {
            OutlinedTextField(
                value = codNote,
                onValueChange = { codNote = it },
                label = { Text("Reason for COD / Delivery Note") },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onOrderPlaced,
            enabled = selectedMethod.isNotEmpty(),
            modifier = Modifier.fillMaxWidth().height(55.dp)
        ) {
            Text(if (selectedMethod == "COD") "Request Approval" else "Place Order")
        }
    }
}
