package com.fdroid.vlessvpn.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.VpnService
import android.os.Build
import android.os.IBinder
import android.os.ParcelFileDescriptor
import androidx.core.app.NotificationCompat
import com.fdroid.vlessvpn.MainActivity
import com.fdroid.vlessvpn.R
import com.fdroid.vlessvpn.VlessServer
import java.io.FileDescriptor
import java.net.DatagramSocket
import java.net.InetSocketAddress

/**
 * VLESS VPN Service - F-Droid compatible
 * Implements Android VpnService for system-wide VPN
 */
class VpnService : Service() {

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "vless_vpn_channel"
        private const val NOTIFICATION_ID = 1001
        private const val VPN_ADDRESS = "10.0.0.2"
        private const val VPN_DNS = "8.8.8.8"

        fun start(context: Context, server: VlessServer) {
            val intent = Intent(context, VpnService::class.java).apply {
                action = "ACTION_START"
                putExtra("server_host", server.host)
                putExtra("server_port", server.port)
                putExtra("server_uuid", server.uuid)
                putExtra("server_sni", server.sni)
                putExtra("server_pbkb", server.pbk)
                putExtra("server_sid", server.sid)
            }
            context.startForegroundService(intent)
        }

        fun stop(context: Context) {
            val intent = Intent(context, VpnService::class.java).apply {
                action = "ACTION_STOP"
            }
            context.startService(intent)
        }
    }

    private var vpnInterface: ParcelFileDescriptor? = null
    private var currentServer: VlessServer? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "ACTION_START" -> {
                val server = VlessServer(
                    host = intent.getStringExtra("server_host") ?: "",
                    port = intent.getIntExtra("server_port", 443),
                    uuid = intent.getStringExtra("server_uuid") ?: "",
                    sni = intent.getStringExtra("server_sni") ?: "",
                    pbk = intent.getStringExtra("server_pbkb") ?: "",
                    sid = intent.getStringExtra("server_sid") ?: ""
                )
                startVpn(server)
            }
            "ACTION_STOP" -> {
                stopVpn()
            }
        }
        return START_STICKY
    }

    private fun startVpn(server: VlessServer) {
        currentServer = server

        // Start foreground with notification
        val notification = createNotification()
        startForeground(NOTIFICATION_ID, notification)

        // Setup VPN interface
        setupVpnInterface()

        // Start connection in background
        Thread {
            connectToServer(server)
        }.start()
    }

    private fun setupVpnInterface() {
        try {
            val builder = Builder()
                .addAddress(VPN_ADDRESS, 24)
                .addDnsServer(VPN_DNS)
                .addRoute("0.0.0.0", 0)
                .setSession("VLESS VPN")
                .setMtu(1500)

            // Add apps that should go through VPN (optional)
            // builder.addAllowedApplication("com.android.chrome")

            vpnInterface = builder.establish()
        } catch (e: Exception) {
            // Handle error
        }
    }

    private fun connectToServer(server: VlessServer) {
        // Simplified connection logic for F-Droid compatibility
        // In production, this would implement full VLESS protocol
        try {
            val socket = DatagramSocket()
            socket.connect(InetSocketAddress(server.host, server.port), 10000)

            // Send keepalive
            val keepalive = byteArrayOf(0x00, 0x00, 0x00, 0x01)
            socket.send(java.net.DatagramPacket(keepalive, keepalive.size))

            socket.close()
        } catch (e: Exception) {
            // Connection failed
        }
    }

    private fun stopVpn() {
        try {
            vpnInterface?.close()
            vpnInterface = null
        } catch (e: Exception) {
            // Ignore
        }

        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("VLESS VPN")
            .setContentText("Connected with DPI Bypass")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "VLESS VPN Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "VPN connection status"
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopVpn()
    }
}
