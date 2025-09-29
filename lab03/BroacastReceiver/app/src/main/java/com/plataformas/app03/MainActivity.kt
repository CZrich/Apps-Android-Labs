package com.plataformas.app03

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import com.plataformas.app03.utils.BatteryReceiver

class MainActivity : AppCompatActivity() {
    private lateinit var batteryReceiver: BatteryReceiver

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        batteryReceiver = BatteryReceiver()
    }
    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(batteryReceiver, filter)
        Log.d("MainActivity", "BroadcastReceiver registrado satisfactoriamente.")
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(batteryReceiver)
        Log.d("MainActivity", "BroadcastReceiver desregistrado satisfactoriamente.")
    }
}