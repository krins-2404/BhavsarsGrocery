package com.example.bhavsarsgrocery

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AdminDashboard(
    onNavigateToAddProduct: () -> Unit,
    onNavigateToOrders: () -> Unit,
    onNavigateToWholesaler: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Business Overview", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))

        // First Row: Daily Stats
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DashboardCard("Orders Today", "12", Modifier.weight(1f))
            DashboardCard("Earnings", "₹4,500", Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Second Row: Inventory & Debt
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DashboardCard("Stock Value", "₹25,000", Modifier.weight(1f))
            DashboardCard("Due to Wholesaler", "₹1,200", Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- NAVIGATION SECTION ---
        Text("Quick Actions", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(12.dp))

        // 1. View Orders Button
        Button(
            onClick = onNavigateToOrders,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Manage Customer Orders")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 2. Add Product Button
        OutlinedButton(
            onClick = onNavigateToAddProduct,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add New Inventory/Product")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 3. Wholesaler Ledger Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
            onClick = onNavigateToWholesaler
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Wholesaler Ledger (Khata)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("Record payments to suppliers and stock arrivals.", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun DashboardCard(title: String, value: String, modifier: Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.labelMedium)
            Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
    }
}
