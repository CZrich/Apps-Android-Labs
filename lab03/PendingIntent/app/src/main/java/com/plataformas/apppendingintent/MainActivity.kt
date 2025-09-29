package com.plataformas.apppendingintent

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity

import com.plataformas.apppendingintent.utils.BatteryReceiverPending

class MainActivity : AppCompatActivity() {
    private lateinit var receiverPending: BatteryReceiverPending

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        receiverPending = BatteryReceiverPending()
    }

    override fun onResume() {
        super.onResume()

        // Registrar el receiver
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(receiverPending, filter)
        Log.d("MainActivity", "Receiver con PendingIntent registrado.")

        // Crear PendingIntent
        val intent = Intent(this, BatteryReceiverPending::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            pendingIntent.send() // Dispara el Broadcast
        } catch (e: PendingIntent.CanceledException) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiverPending)
        Log.d("MainActivity", "Receiver con PendingIntent desregistrado.")
    }
}