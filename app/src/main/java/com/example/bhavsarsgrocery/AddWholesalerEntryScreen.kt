package com.example.bhavsarsgrocery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AddWholesalerEntryScreen(onEntrySaved: (WholesalerTransaction) -> Unit) {
    var wholesalerName by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var personName by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }

    // NEW: State to track if it's Stock or Payment
    var isStockArrival by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Wholesaler Entry", style = MaterialTheme.typography.headlineSmall)

        // Mode Selector
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
            Button(
                onClick = { isStockArrival = true },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isStockArrival) MaterialTheme.colorScheme.primary else Color.LightGray
                )
            ) { Text("Stock Arrival") }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { isStockArrival = false },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isStockArrival) MaterialTheme.colorScheme.primary else Color.LightGray
                )
            ) { Text("Payment Given") }
        }

        OutlinedTextField(
            value = wholesalerName,
            onValueChange = { wholesalerName = it },
            label = { Text("Wholesaler Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Label changes based on selection
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text(if (isStockArrival) "Bill Amount (₹)" else "Amount Paid (₹)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (!isStockArrival) {
            OutlinedTextField(
                value = personName,
                onValueChange = { personName = it },
                label = { Text("Whom was the money given to?") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = details,
            onValueChange = { details = it },
            label = { Text(if (isStockArrival) "Items Details (e.g. 50L Milk)" else "Payment Note (e.g. Cash)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val newRecord = WholesalerTransaction(
                    wholesalerName = wholesalerName,
                    date = "20-03-2026",
                    type = if (isStockArrival) "STOCK" else "PAYMENT",
                    paymentGivenTo = if (isStockArrival) "N/A" else personName,
                    amount = amount.toDoubleOrNull() ?: 0.0,
                    details = details,
                    deliveryStatus = if (isStockArrival) "Received" else "Confirmed"
                )
                onEntrySaved(newRecord)
            },
            modifier = Modifier.fillMaxWidth().height(55.dp)
        ) {
            Text("Save Record")
        }
    }
}


