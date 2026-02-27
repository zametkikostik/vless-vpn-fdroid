package com.fdroid.vlessvpn

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fdroid.vlessvpn.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

/**
 * VLESS VPN - Main Activity
 * F-Droid compatible VPN client with DPI bypass
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vpnViewModel: VpnViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.connectButton.setOnClickListener {
            if (vpnViewModel.isConnected.value == true) {
                vpnViewModel.disconnect()
            } else {
                vpnViewModel.connect()
            }
        }

        binding.scanButton.setOnClickListener {
            vpnViewModel.scanServers()
        }

        binding.settingsButton.setOnClickListener {
            // Open settings
        }
    }

    private fun observeViewModel() {
        vpnViewModel.isConnected.observe(this) { connected ->
            updateConnectionState(connected)
        }

        vpnViewModel.statusMessage.observe(this) { message ->
            binding.statusText.text = message
        }

        vpnViewModel.serverCount.observe(this) { count ->
            binding.serverCountText.text = "Servers: $count"
        }

        lifecycleScope.launch {
            vpnViewModel.startServerScanner()
        }
    }

    private fun updateConnectionState(connected: Boolean) {
        if (connected) {
            binding.connectButton.text = "Disconnect"
            binding.connectButton.setBackgroundColor(getColor(android.R.color.holo_red_dark))
            binding.statusText.text = "Connected"
        } else {
            binding.connectButton.text = "Connect"
            binding.connectButton.setBackgroundColor(getColor(android.R.color.holo_green_dark))
            binding.statusText.text = "Disconnected"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Don't disconnect VPN on activity destroy
    }
}
