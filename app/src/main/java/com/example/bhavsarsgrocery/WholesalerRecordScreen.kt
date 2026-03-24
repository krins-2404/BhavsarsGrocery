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
        Text("Wholesaler Ledger", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        if (records.isEmpty()) {
            Text("No transactions recorded yet.", color = Color.Gray)
        }

        LazyColumn {
            items(records) { record ->
                val isStock = record.type == "STOCK"

                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(
                        // Light Blue for Stock, Light Green for Payment
                        containerColor = if (isStock) Color(0xFFE3F2FD) else Color(0xFFE8F5E9)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(record.wholesalerName, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Text(record.date, style = MaterialTheme.typography.labelSmall)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Type Badge
                        SuggestionChip(
                            onClick = { },
                            label = { Text(if (isStock) "📦 STOCK ARRIVAL" else "💸 PAYMENT GIVEN") }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Details: ${record.details}", style = MaterialTheme.typography.bodyMedium)

                        if (!isStock) {
                            Text("Received by: ${record.paymentGivenTo}", style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
                        }

                        Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray)

                        Text(
                            text = "Amount: ₹${record.amount}",
                            style = MaterialTheme.typography.titleMedium,
                            color = if (isStock) Color.Black else Color(0xFF2E7D32),
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}
