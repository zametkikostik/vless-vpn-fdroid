package com.fdroid.vlessvpn.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.fdroid.vlessvpn.service.VpnService

/**
 * Boot Receiver - Auto-start VPN on device boot
 * F-Droid compatible
 */
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == "android.intent.action.QUICKBOOT_POWERON") {

            // Only auto-start if user enabled it (check SharedPreferences in production)
            val shouldAutoStart = getAutoStartEnabled(context)

            if (shouldAutoStart) {
                // Start VPN service
                // Note: In production, you would load saved server configuration
                VpnService.start(context, createDefaultServer())
            }
        }
    }

    private fun getAutoStartEnabled(context: Context): Boolean {
        val prefs = context.getSharedPreferences("vless_vpn_prefs", Context.MODE_PRIVATE)
        return prefs.getBoolean("auto_start", false)
    }

    private fun createDefaultServer() = com.fdroid.vlessvpn.VlessServer(
        host = "81.200.149.222",
        port = 443,
        uuid = "1c1d7040-828e-4fcc-8406-d8545a30c2ff",
        country = "🌍"
    )
}
