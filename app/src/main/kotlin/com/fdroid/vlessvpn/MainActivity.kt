package com.fdroid.vlessvpn

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

/**
 * VLESS VPN - Main Activity
 * F-Droid compatible VPN client with DPI bypass
 */
class MainActivity : AppCompatActivity() {

    private lateinit var vpnViewModel: VpnViewModel
    private lateinit var statusText: TextView
    private lateinit var connectButton: Button
    private lateinit var scanButton: Button
    private lateinit var serverCountText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        statusText = findViewById(R.id.statusText)
        connectButton = findViewById(R.id.connectButton)
        scanButton = findViewById(R.id.scanButton)
        serverCountText = findViewById(R.id.serverCountText)

        // Initialize ViewModel
        vpnViewModel = VpnViewModel(application)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        connectButton.setOnClickListener {
            if (vpnViewModel.isConnected.value == true) {
                vpnViewModel.disconnect()
            } else {
                vpnViewModel.connect()
            }
        }

        scanButton.setOnClickListener {
            vpnViewModel.scanServers()
            Toast.makeText(this, "Scanning servers...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        vpnViewModel.isConnected.observe(this) { connected ->
            updateConnectionState(connected)
        }

        vpnViewModel.statusMessage.observe(this) { message ->
            statusText.text = message
        }

        vpnViewModel.serverCount.observe(this) { count ->
            serverCountText.text = "Servers: $count"
        }

        lifecycleScope.launch {
            vpnViewModel.startServerScanner()
        }
    }

    private fun updateConnectionState(connected: Boolean) {
        if (connected) {
            connectButton.text = "Disconnect"
            connectButton.setBackgroundColor(getColor(android.R.color.holo_red_dark))
            statusText.text = "Connected"
        } else {
            connectButton.text = "Connect"
            connectButton.setBackgroundColor(getColor(android.R.color.holo_green_dark))
            statusText.text = "Disconnected"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Don't disconnect VPN on activity destroy
    }
}
