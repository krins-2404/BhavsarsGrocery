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
    productList: List<GroceryItem>,
    orders: List<Order>,
    onNavigateToAddProduct: () -> Unit,
    onNavigateToOrders: () -> Unit,
    onNavigateToWholesaler: () -> Unit
) {
    // ✅ NEW: The app does the math here before drawing the screen
    val totalEarnings = orders.sumOf { it.totalAmount }
    val lowStockItems = productList.filter { it.stockQty < 5 }
    val totalStockValue = productList.sumOf { it.price * it.stockQty }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Business Overview", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // --- 🚨 LOW STOCK ALERT ---
        if (lowStockItems.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)) // Light Red
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("⚠️ Low Stock Alert", color = Color.Red, fontWeight = FontWeight.Bold)
                    // Show up to 2 items so the box doesn't get too huge
                    lowStockItems.take(2).forEach { item ->
                        Text("${item.name} only has ${item.stockQty} left!", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        // First Row: Daily Stats (Now using live data!)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DashboardCard("Orders Today", "${orders.size}", Modifier.weight(1f))
            DashboardCard("Earnings", "₹$totalEarnings", Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Second Row: Inventory & Debt (Now using live data!)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DashboardCard("Stock Value", "₹$totalStockValue", Modifier.weight(1f))
            DashboardCard("Due to Wholesaler", "₹1,200", Modifier.weight(1f)) // We can make this live later!
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- NAVIGATION SECTION ---
        Text("Quick Actions", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(12.dp))

        // 1. View Orders Button (Removed the duplicate!)
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
