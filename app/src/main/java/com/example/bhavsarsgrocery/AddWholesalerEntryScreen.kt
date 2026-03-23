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
    var receivedBy by remember { mutableStateOf("") }
    var itemsDetail by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("20-03-2026") } // You can automate this later

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Record Wholesaler Visit", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(20.dp))

        // Who is the Supplier?
        OutlinedTextField(
            value = wholesalerName,
            onValueChange = { wholesalerName = it },
            label = { Text("Wholesaler Name (e.g. Amul, Parle)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // What did they deliver?
        OutlinedTextField(
            value = itemsDetail,
            onValueChange = { itemsDetail = it },
            label = { Text("Items Delivered (e.g. 10 Bags Sugar)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // How much money was given?
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount Paid (₹)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Who took the money?
        OutlinedTextField(
            value = receivedBy,
            onValueChange = { receivedBy = it },
            label = { Text("Money Given To (Person's Name)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (wholesalerName.isNotEmpty() && amount.isNotEmpty()) {
                    val newRecord = WholesalerTransaction(
                        wholesalerName = wholesalerName,
                        date = date,
                        paymentGivenTo = receivedBy,
                        amountPaid = amount.toDoubleOrNull() ?: 0.0,
                        orderDetails = itemsDetail,
                        deliveryStatus = "Received"
                    )
                    onEntrySaved(newRecord)
                }
            },
            modifier = Modifier.fillMaxWidth().height(55.dp)
        ) {
            Text("Save to Ledger")
        }
    }
}
