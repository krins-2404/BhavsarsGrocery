package com.example.bhavsarsgrocery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdminStockScreen() {
    // This would eventually be linked to your database
    var products by remember { mutableStateOf(listOf(
        GroceryItem(name = "Milk", category = "Dairy", price = 60.0, isAvailable = true),
        GroceryItem(name = "Festival Sweets", category = "Seasonal", price = 300.0, isAvailable = false)
    ))}

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Manage Availability", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(products) { item ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(item.name, style = MaterialTheme.typography.bodyLarge)

                    // Toggle Switch for availability
                    Switch(
                        checked = item.isAvailable,
                        onCheckedChange = { /* Update status in DB here */ }
                    )
                }
                Divider()
            }
        }
    }
}
