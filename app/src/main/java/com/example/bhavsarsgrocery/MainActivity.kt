package com.example.bhavsarsgrocery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bhavsarsgrocery.ui.theme.BhavsarsGroceryTheme

// IMPORTANT: If CustomerLoginScreen is in another file,
// you may need to manually import it if Alt+Enter doesn't work:
// import com.example.bhavsarsgrocery.CustomerLoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BhavsarsGroceryTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(navController = navController, startDestination = "welcome") {
                            composable("welcome") {
                                WelcomeScreen(
                                    onCustomerClick = { navController.navigate("login") },
                                    onAdminClick = { navController.navigate("admin_login") }
                                )
                            }
                            composable("login") {
                                CustomerLoginScreen(// Now, clicking "Send OTP" goes to the verification screen
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
                                CategoryScreen(onCategoryClick = { /* logic */})

                            }
                            composable("admin_login") {
                                // We will create this screen next!
                                AdminLoginScreen(onAdminLoginSuccess = {
                                    navController.navigate("admin_dashboard")
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}

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
