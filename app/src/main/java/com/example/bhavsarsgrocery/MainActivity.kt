package com.example.bhavsarsgrocery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bhavsarsgrocery.ui.theme.BhavsarsGroceryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BhavsarsGroceryTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {

                        // --- SHARED DATA LISTS ---
                        val globalProductList = remember { mutableStateListOf<GroceryItem>() }
                        val activeOrders = remember { mutableStateListOf<Order>() }
                        // FIX: Added the history list for wholesalers
                        val wholesalerHistory = remember { mutableStateListOf<WholesalerTransaction>() }

                        NavHost(navController = navController, startDestination = "welcome") {

                            // 1. WELCOME
                            composable("welcome") {
                                WelcomeScreen(
                                    onCustomerClick = { navController.navigate("login") },
                                    onAdminClick = { navController.navigate("admin_login") }
                                )
                            }

                            // 2. CUSTOMER FLOW
                            composable("login") {
                                CustomerLoginScreen(
                                    onOtpSent = { phoneNumber ->
                                        navController.navigate("otp_verify/$phoneNumber")
                                    }
                                )
                            }
                            composable("otp_verify/{phoneNumber}") { backStackEntry ->
                                val phone = backStackEntry.arguments?.getString("phoneNumber") ?: ""
                                OtpVerificationScreen(
                                    phoneNumber = phone,
                                    onVerificationSuccess = { navController.navigate("categories") }
                                )
                            }
                            composable("categories") {
                                CategoryScreen(
                                    onCategoryClick = { name ->
                                        navController.navigate("product_list/$name")
                                    }
                                )
                            }
                            composable("product_list/{categoryName}") { backStackEntry ->
                                val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                                CustomerProductListScreen(
                                    categoryName = categoryName,
                                    allProducts = globalProductList
                                )
                            }
                            composable("payment/{amount}") { backStackEntry ->
                                val amount = backStackEntry.arguments?.getString("amount")?.toDouble() ?: 0.0
                                PaymentScreen(totalAmount = amount) {
                                    navController.navigate("order_success")
                                }
                            }

                            // 3. ADMIN FLOW
                            composable("admin_login") {
                                AdminLoginScreen(onAdminLoginSuccess = {
                                    navController.navigate("admin_dashboard")
                                })
                            }
                            composable("admin_dashboard") {
                                AdminDashboard(
                                    // ✅ Add these two lines so the dashboard can do its math!
                                    productList = globalProductList,
                                    orders = activeOrders,
                                    onNavigateToAddProduct = { navController.navigate("admin_add_product") },
                                    onNavigateToOrders = { navController.navigate("admin_orders") },
                                    onNavigateToWholesaler = { navController.navigate("wholesaler_ledger") }
                                )
                            }
                            // FIX: Corrected the syntax here
                            composable("wholesaler_ledger") {
                                WholesalerRecordScreen(records = wholesalerHistory)
                            }
                            // 2. Route to add a new entry
                            composable("add_wholesaler_entry") {
                                AddWholesalerEntryScreen(onEntrySaved = { newRecord ->
                                    wholesalerHistory.add(newRecord)
                                    navController.popBackStack() // This takes you back to the list automatically
                                })
                            }
                            composable("admin_orders") {
                                OrderApprovalScreen(orders = activeOrders)
                            }
                            composable("admin_add_product") {
                                AdminAddProductScreen(onProductAdded = { newItem ->
                                    globalProductList.add(newItem)
                                    navController.popBackStack()
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}


// ... WelcomeScreen and WelcomePreview stay exactly as they were ...
@Composable
fun WelcomeScreen(onCustomerClick: () -> Unit, onAdminClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "BHAVSAR'S",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Your trusted village shop, now on your phone.",
            style = MaterialTheme.typography.bodySmall,
            fontFamily = androidx.compose.ui.text.font.FontFamily.Cursive,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = onCustomerClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text("Customer Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onAdminClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text("Admin Login")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    BhavsarsGroceryTheme {
        WelcomeScreen(onCustomerClick = {}, onAdminClick = {})
    }
}
