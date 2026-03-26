package com.example.bhavsarsgrocery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class) // Required for the Dropdown Menu in Material 3
@Composable
fun AdminAddProductScreen(onProductAdded: (GroceryItem) -> Unit) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productCategory by remember { mutableStateOf("Daily Grocery") }
    var productWeight by remember { mutableStateOf("") } // e.g., 1kg or 500g

    val categories = listOf("Daily Grocery", "Dairy & Eggs", "Festival Items", "Seasonal Items")
    // States for the dropdown menu
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(categories[0]) } // Defaults to the first one

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Add New Product", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        // Product Name
        OutlinedTextField(
            value = productName,
            onValueChange = { productName = it },
            label = { Text("Product Name (e.g. Basmati Rice)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))
        // 2. Category Dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = {},
                readOnly = true, // Forces them to tap the dropdown, not type freely
                label = { Text("Select Category") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            // The list that pops out when tapped
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedCategory = selectionOption
                            expanded = false // Close the menu after clicking
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Price
        OutlinedTextField(
            value = productPrice,
            onValueChange = { productPrice = it },
            label = { Text("Price (in ₹)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Weight/Unit
        OutlinedTextField(
            value = productWeight,
            onValueChange = { productWeight = it },
            label = { Text("Weight/Unit (e.g. 1kg)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Simple Category Picker (For now, we just use the first one)
        Text("Category: $productCategory", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (productName.isNotEmpty() && productPrice.isNotEmpty()) {
                    val newItem = GroceryItem(
                        name = productName,
                        category = productCategory,
                        price = productPrice.toDoubleOrNull() ?: 0.0,
                        stockQty = 100 // Default stock
                    )
                    onProductAdded(newItem)
                    // Reset fields
                    productName = ""
                    productPrice = ""
                    productWeight = ""
                }
            },
            modifier = Modifier.fillMaxWidth().height(55.dp)
        ) {
            Text("Add to Shop List")
        }
    }
}
