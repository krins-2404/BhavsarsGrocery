# 🛒 Bhavsar's Grocery App

A modern, "village-smart" Android application built with **Kotlin** and **Jetpack Compose**. 

This app is designed to bridge the gap between traditional rural/local grocery shops and modern e-commerce. It features a complete dual-user system: a streamlined shopping experience for customers and a powerful, offline-first dashboard for the shop owner (Admin).

## ✨ Key Features

### 🧑‍💼 Admin (Shop Owner) Flow
* **Live Dashboard:** Instantly view daily earnings, pending wholesaler debts, and active orders.
* **Smart Stock Alerts:** Automatic red-flag warnings for items running low on inventory.
* **Inventory Management:** Group products by category, update prices in real-time with popup dialogs, and delete discontinued items.
* **Wholesaler Ledger (Khata):** Track incoming stock bills and outgoing payments to suppliers.

### 🛍️ Customer Flow
* **Flexible Login:** Login via Email or Phone Number (featuring a dynamic Country Code selector).
* **Smart Catalog:** Browse items by category tabs or use the live Search Bar to find specific goods.
* **Interactive Cart:** Add items, adjust quantities (+/-), and see real-time price totals.
* **Village-Smart Delivery:** Hybrid distance calculation using background GPS combined with manual landmark entry (e.g., "Opposite the blue gate").
* **Payment Selection:** Choose between standard UPI/Online payment or Cash on Delivery (COD) with custom delivery instructions.

## 🛠️ Tech Stack
* **Language:** Kotlin
* **UI Toolkit:** Jetpack Compose (Material Design 3)
* **Architecture:** State Management using `mutableStateListOf` and `remember`
* **Navigation:** Jetpack Navigation Compose
* **Animation:** Compose `animateFloatAsState` (Spring bouncing animations)

## 📱 Screenshots
*(Note: Replace these placeholder links with actual screenshots of your app later!)*

| Customer Home | Smart Cart | Admin Dashboard |
|:---:|:---:|:---:|
| <img src="link_to_home_image" width="200"> | <img src="link_to_cart_image" width="200"> | <img src="link_to_admin_image" width="200"> |

## 🚀 How to Run the Project
1. Clone this repository:
   ```bash
   git clone [https://github.com/your-username/bhavsars-grocery.git](https://github.com/your-username/bhavsars-grocery.git)
