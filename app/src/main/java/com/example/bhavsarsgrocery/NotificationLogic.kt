package com.example.bhavsarsgrocery

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object OrderNotificationManager {

    // This opens WhatsApp with a pre-filled message for the customer
    fun sendWhatsAppNotification(context: Context, phoneNumber: String, customerName: String, status: String, note: String = "") {
        val message = "Hello $customerName! Your order at Bhavsar's Grocery has been $status. \nNote: $note \nThank you for shopping!"

        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://api.whatsapp.com/send?phone=91$phoneNumber&text=${Uri.encode(message)}")
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "WhatsApp not installed", Toast.LENGTH_SHORT).show()
        }
    }
}
