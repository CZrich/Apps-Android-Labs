package com.plataformas.apppendingintent.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.util.Log
import android.widget.Toast

class BatteryReceiverPending : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1

        val statusStr = when (status) {
            BatteryManager.BATTERY_STATUS_CHARGING -> "Cargando"
            BatteryManager.BATTERY_STATUS_DISCHARGING -> "Descargando"
            BatteryManager.BATTERY_STATUS_FULL -> "Batería llena"
            else -> "Desconocido"
        }

        val msg = "[PendingIntent] Nivel: $level%, Estado: $statusStr"
        Log.d("BatteryReceiverPending", msg)
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

}