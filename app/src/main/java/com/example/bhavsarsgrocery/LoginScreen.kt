package com.example.bhavsarsgrocery

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerLoginScreen(onOtpSent: (String) -> Unit) {
    var name by remember { mutableStateOf("") }
    // State to toggle between Phone and Email
    val loginOptions = listOf("Phone Number", "Email Address")
    var selectedOption by remember { mutableStateOf(loginOptions[0]) }
    // Input state
    var inputValue by remember { mutableStateOf("") }
    // Country Code States
    val countries = listOf(
        "+91" to "India",
        "+1" to "USA / Canada",
        "+44" to "UK",
        "+61" to "Australia",
        "+971" to "UAE"
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf(countries[0]) }


    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome to Bhavsar's", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("Login or create an account to start shopping", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Spacer(modifier = Modifier.height(32.dp))

        // Name Input
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter Your Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))
        // 1. Toggle Tabs for Phone vs Email
        TabRow(
            selectedTabIndex = loginOptions.indexOf(selectedOption),
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
        ) {
            loginOptions.forEach { option ->
                Tab(
                    selected = selectedOption == option,
                    onClick = {
                        selectedOption = option
                        inputValue = "" // Clear input when switching
                    },
                    text = { Text(option, fontWeight = FontWeight.SemiBold) }
                )
            }
        }
        // 2. Dynamic Input Fields
        if (selectedOption == "Phone Number") {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

                // Country Code Dropdown
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.weight(0.35f)
                ) {
                    OutlinedTextField(
                        value = selectedCountry.first, // Shows just the code (e.g., +91)
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        countries.forEach { country ->
                            DropdownMenuItem(
                                text = { Text("${country.first} ${country.second}") },
                                onClick = {
                                    selectedCountry = country
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Phone Number Input
                OutlinedTextField(
                    value = inputValue,
                    onValueChange = {
                        // Only allow numbers
                        if (it.all { char -> char.isDigit() }) inputValue = it
                    },
                    label = { Text("Phone Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(0.65f),
                    singleLine = true
                )
            }
        } else {
            // Email Input
            OutlinedTextField(
                value = inputValue,
                onValueChange = { inputValue = it },
                label = { Text("Email Address") },
                placeholder = { Text("e.g. name@example.com") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 3. Submit Button
        Button(
            onClick = {
                // Combine country code and number if phone is selected, otherwise just use email
                val finalContactInfo = if (selectedOption == "Phone Number") {
                    "${selectedCountry.first}$inputValue"
                } else {
                    inputValue
                }

                onOtpSent(finalContactInfo)
            },
            enabled = inputValue.isNotBlank(),
            modifier = Modifier.fillMaxWidth().height(55.dp)
        ) {
            Text("Send OTP / Login Code")
        }
    }
}
