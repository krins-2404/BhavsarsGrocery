package com.example.bhavsarsgrocery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomerProductListScreen(categoryName: String, allProducts: List<GroceryItem>) {
    // 1. Filter the products based on the category the user clicked
    val filteredProducts = allProducts.filter { it.category == categoryName }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Available $categoryName", style = MaterialTheme.typography.headlineMedium)

        if (filteredProducts.isEmpty()) {
            Text(
                text = "No items available in this category yet.",
                modifier = Modifier.padding(top = 20.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                // FIX: Changed 'sampleProducts' to 'filteredProducts'
                items(filteredProducts) { product ->
                    ProductItemRow(product)
                }
            }
        }
    }
}

@Composable
fun ProductItemRow(product: GroceryItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Column {
                Text(text = product.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "₹${product.price}", color = Color.Gray)
            }

            // Add to Cart Button
            Button(onClick = { /* We will add Cart Logic in the next phase */ }) {
                Text("Add")
            }
        }
    }
}
