package com.example.bhavsarsgrocery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.* // ✅ Import for 'remember' and 'mutableStateOf'
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CustomerProductListScreen(categoryName: String,
                              allProducts: List<GroceryItem>,
                              cartItems: MutableList<GroceryItem>,
                              onViewCart: () -> Unit
) {
    // 1. Get unique categories from the products the admin added
    val categories = allProducts.map { it.category }.distinct()
    var selectedCategory by remember { mutableStateOf(if (categories.isNotEmpty()) categories[0] else "") }
    // --- 🔍 NEW: SEARCH STATE ---
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            if (cartItems.isNotEmpty()) {
                ExtendedFloatingActionButton(
                    onClick = onViewCart,
                    icon = { Icon(Icons.Default.ShoppingCart, null) },
                    text = { Text("View Cart (${cartItems.size})") },
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            Text("Bhavsar's Grocery Shop", style = MaterialTheme.typography.headlineMedium)
            // --- 🔍 SEARCH BAR UI ---
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search Sugar, Milk, etc...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = MaterialTheme.shapes.medium,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Category Selector (Horizontal Scroll)
            if (categories.isNotEmpty()) {
                ScrollableTabRow(
                    selectedTabIndex = categories.indexOf(selectedCategory).coerceAtLeast(0),
                    edgePadding = 0.dp,
                    divider = {}
                ) {
                    categories.forEach { category ->
                        Tab(
                            selected = selectedCategory == category,
                            onClick = { selectedCategory = category
                                        searchQuery = "" // Reset search when category changes
                                      },
                            text = { Text(category) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            // 3. Filter the products based on the category the user clicked
            val filteredProducts = allProducts.filter {
                it.category == categoryName &&
                    it.name.contains(searchQuery, ignoreCase = true)
            }

            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text(
                    text = "Available $categoryName",
                    style = MaterialTheme.typography.headlineMedium
                )

                if (filteredProducts.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "No items available in this category yet.",
                            modifier = Modifier.padding(top = 20.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        // FIX: Changed 'sampleProducts' to 'filteredProducts'
                        items(filteredProducts) { product ->
                            ProductItemRow(
                                product = product,
                                onAddToCart = { cartItems.add(product) }
                            )
                        }
                    }
                }
            }
        }

    }
}
@Composable
fun ProductItemRow(product: GroceryItem, onAddToCart: () -> Unit) {
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
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "₹${product.price}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFF388E3C)
                        )
                    }

                    // Add to Cart Button
                    Button(
                        onClick = onAddToCart,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    )
                    {
                        Text("Add")
                    }
                }
            }
}



