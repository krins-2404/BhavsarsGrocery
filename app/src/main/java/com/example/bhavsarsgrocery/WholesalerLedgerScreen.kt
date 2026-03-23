package com.example.bhavsarsgrocery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class WholesalerOrder(
    val supplierName: String,
    val itemsReceived: String,
    val billAmount: Double,
    val isPaid: Boolean,
    val date: String
)

@Composable
fun WholesalerLedgerScreen() {
    // Sample data - eventually this comes from your database
    val history = remember { mutableStateListOf(
        WholesalerOrder("Amul Dairy", "50L Milk, 20pk Butter", 4200.0, true, "Mar 18"),
        WholesalerOrder("Hindustan Unilever", "Soap, Shampoo, Surf", 8500.0, false, "Mar 19"),
        WholesalerOrder("Local Grain Merchant", "500kg Wheat", 12000.0, false, "Mar 20")
    )}

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Wholesaler Payments", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(history) { order ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(order.supplierName, style = MaterialTheme.typography.titleMedium)
                            Text(order.itemsReceived, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                            Text("Date: ${order.date}", style = MaterialTheme.typography.labelSmall)
                        }

                        Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                            Text("₹${order.billAmount}", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)

                            // Status Badge
                            Surface(
                                color = if (order.isPaid) Color(0xFFC8E6C9) else Color(0xFFFFCDD2),
                                shape = MaterialTheme.shapes.small
                            ) {
                                Text(
                                    text = if (order.isPaid) "PAID" else "UNPAID",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (order.isPaid) Color(0xFF2E7D32) else Color(0xFFC62828)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
