package com.example.bhavsarsgrocery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun OrderApprovalScreen(orders: MutableList<Order>) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Incoming Customer Orders", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        if (orders.isEmpty()) {
            Text("No new orders yet.", color = Color.Gray)
        }

        LazyColumn {
            // itemsIndexed helps us identify exactly which order to update or remove
            itemsIndexed(orders) { index, order ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Order #${order.id}", style = MaterialTheme.typography.labelLarge)
                            // Show status with different colors
                            Text(
                                text = order.status,
                                color = if (order.status == "Pending") Color.Red else Color(0xFF388E3C),
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(order.customerName, style = MaterialTheme.typography.titleLarge)
                        Text("Phone: ${order.customerPhone}", style = MaterialTheme.typography.bodySmall)

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        order.items.forEach { item ->
                            Text("• ${item.name} (₹${item.price})", style = MaterialTheme.typography.bodyMedium)
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Total Amount: ₹${order.totalAmount}", fontWeight = FontWeight.Bold)

                        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.End) {
                            // REJECT BUTTON: Removes the order from the list
                            OutlinedButton(onClick = {
                                orders.removeAt(index)
                            }) {
                                Text("Reject", color = Color.Red)
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // ACCEPT BUTTON: Updates the status to "Packed"
                            Button(onClick = {
                                orders[index] = order.copy(status = "Packed & Ready")
                            }) {
                                Text("Accept & Pack")
                            }
                        }
                    }
                }
            }
        }
    }
}
