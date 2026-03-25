package com.example.bhavsarsgrocery

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun AdminFinanceScreen() {
    // These numbers will come from your database later
    val dailyEarnings = 5400.0
    val stockSpending = 3200.0
    val profit = dailyEarnings - stockSpending

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Account Details", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        // Earnings Card
        FinanceRow("Total Earned Today", "₹$dailyEarnings", Color(0xFF388E3C))

        // Spending Card
        FinanceRow("Spent on Stock", "₹$stockSpending", Color(0xFFD32F2F))

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        // Net Profit
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Net Profit", style = MaterialTheme.typography.titleLarge)
            Text(
                "₹$profit",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = if (profit >= 0) Color(0xFF388E3C) else Color.Red
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Stock Stats
        Text("Stock Status", style = MaterialTheme.typography.titleMedium)
        Text("New stock arrived today: 5 items", style = MaterialTheme.typography.bodyMedium)
        Text("Items remaining to pay: ₹1,500", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun FinanceRow(label: String, value: String, valueColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label)
            Text(value, color = valueColor, fontWeight = FontWeight.Bold)
        }
    }
}
