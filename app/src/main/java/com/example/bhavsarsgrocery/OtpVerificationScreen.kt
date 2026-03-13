package com.example.bhavsarsgrocery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun OtpVerificationScreen(phoneNumber: String, onVerificationSuccess: () -> Unit) {
    var otpCode by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Verify Your Number", style = MaterialTheme.typography.headlineSmall)
        Text("Code sent to $phoneNumber", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = otpCode,
            onValueChange = { if (it.length <= 6) otpCode = it },
            label = { Text("Enter OTP") },
            placeholder = { Text("123456") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = isError
        )

        if (isError) {
            Text("Invalid OTP. Please try again.", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // Dummy logic: If code is 123456, let them in!
                if (otpCode == "123456") {
                    onVerificationSuccess()
                } else {
                    isError = true
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Verify & Continue")
        }

        TextButton(onClick = { /* Logic to resend OTP */ }) {
            Text("Resend Code")
        }
    }
}
