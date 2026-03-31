package com.example.bhavsarsgrocery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartItemListScreen(
    cartItems: MutableList<GroceryItem>,
    onBack: () -> Unit,
    onProceedToDelivery: (Double) -> Unit
) {
    // 1. Group the items by their ID so we know the quantities
    // This turns a list of [Milk, Milk, Sugar] into -> {Milk: 2, Sugar: 1}
    val groupedCart = cartItems.groupBy { it.id }.values.toList()

    // 2. Calculate the grand total
    val totalAmount = cartItems.sumOf { it.price }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Shopping Cart") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                Surface(
                    shadowElevation = 8.dp,
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Subtotal:", style = MaterialTheme.typography.titleMedium)
                            Text("₹$totalAmount", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = { onProceedToDelivery(totalAmount) },
                            modifier = Modifier.fillMaxWidth().height(55.dp)
                        ) {
                            Text("Proceed to Delivery Details")
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            if (cartItems.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Your cart is empty.", color = Color.Gray, style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(groupedCart) { itemGroup ->
                        val product = itemGroup.first() // The product details
                        val quantity = itemGroup.size   // How many of this product are in the cart

                        CartItemRow(
                            product = product,
                            quantity = quantity,
                            onAdd = { cartItems.add(product) },
                            onRemove = {
                                // Find the first instance of this item and remove it
                                val indexToRemove = cartItems.indexOfFirst { it.id == product.id }
                                if (indexToRemove != -1) {
                                    cartItems.removeAt(indexToRemove)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    product: GroceryItem,
    quantity: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left Side: Name and Price
            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = "₹${product.price} each", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Text(
                    text = "Total: ₹${product.price * quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Right Side: Quantity Controls (+ / -)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clip(RoundedCornerShape(50)).background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                IconButton(onClick = onRemove) {
                    // Use a minus icon, or a trash can if it's the last item
                    val icon = if (quantity == 1) Icons.Default.Delete else null // If you don't have a minus icon, you can use text "-"
                    if (icon != null) {
                        Icon(icon, contentDescription = "Remove", tint = Color.Red)
                    } else {
                        Text("-", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }
                }

                Text(
                    text = quantity.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                IconButton(onClick = onAdd) {
                    Icon(Icons.Default.Add, contentDescription = "Add", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}
