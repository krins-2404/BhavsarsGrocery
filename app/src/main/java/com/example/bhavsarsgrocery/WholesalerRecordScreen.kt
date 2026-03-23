package com.example.bhavsarsgrocery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun WholesalerRecordScreen(records: List<WholesalerTransaction>) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Wholesaler Transaction History", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(records) { record ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = record.wholesalerName, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Text(text = record.date, style = MaterialTheme.typography.bodySmall)
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        // Delivery Detail
                        Text("Order: ${record.orderDetails}", style = MaterialTheme.typography.bodyMedium)
                        Text("Status: ${record.deliveryStatus}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)

                        Spacer(modifier = Modifier.height(12.dp))

                        // Payment Detail
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = MaterialTheme.shapes.small,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text("Payment of ₹${record.amountPaid}", fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
                                Text("Handed to: ${record.paymentGivenTo}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}
