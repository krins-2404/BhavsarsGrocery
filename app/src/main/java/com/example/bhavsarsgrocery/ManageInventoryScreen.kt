package com.example.bhavsarsgrocery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ManageInventoryScreen(
    productList: MutableList<GroceryItem>,
    onBack: () -> Unit
) {
    // Group products by category automatically
    val categories = productList.map { it.category }.distinct()
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    // State for the Edit Dialog
    var itemToEdit by remember { mutableStateOf<GroceryItem?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Manage Inventory", style = MaterialTheme.typography.headlineMedium)

        if (productList.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No products in shop yet.", color = Color.Gray)
            }
        } else {
            // Category Tabs
            ScrollableTabRow(selectedTabIndex = selectedTabIndex, edgePadding = 0.dp) {
                categories.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // List of items in the selected category
            val filteredItems = productList.filter { it.category == categories[selectedTabIndex] }

            LazyColumn {
                items(filteredItems) { item ->
                    InventoryItemCard(
                        item = item,
                        onEdit = { itemToEdit = item },
                        onDelete = { productList.remove(item) }
                    )
                }
            }
        }
    }

    // --- EDIT POP-UP DIALOG ---
    if (itemToEdit != null) {
        EditProductDialog(
            item = itemToEdit!!,
            onDismiss = { itemToEdit = null },
            onConfirm = { updatedItem ->
                val index = productList.indexOfFirst { it.id == updatedItem.id }
                if (index != -1) {
                    productList[index] = updatedItem
                }
                itemToEdit = null
            }
        )
    }
}

@Composable
fun InventoryItemCard(item: GroceryItem, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("Price: ₹${item.price} | Stock: ${item.stockQty}", style = MaterialTheme.typography.bodySmall)
            }

            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                }
            }
        }
    }
}

@Composable
fun EditProductDialog(item: GroceryItem, onDismiss: () -> Unit, onConfirm: (GroceryItem) -> Unit) {
    var newPrice by remember { mutableStateOf(item.price.toString()) }
    var newStock by remember { mutableStateOf(item.stockQty.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Update ${item.name}") },
        text = {
            Column {
                OutlinedTextField(value = newPrice, onValueChange = { newPrice = it }, label = { Text("Price (₹)") })
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = newStock, onValueChange = { newStock = it }, label = { Text("Stock Quantity") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onConfirm(item.copy(
                    price = newPrice.toDoubleOrNull() ?: item.price,
                    stockQty = newStock.toIntOrNull() ?: item.stockQty
                ))
            }) { Text("Update") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
