package com.fdroid.vlessvpn

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var vpnViewModel: VpnViewModel
    private lateinit var statusText: TextView
    private lateinit var connectButton: Button
    private lateinit var scanButton: Button
    private lateinit var serverCountText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.statusText)
        connectButton = findViewById(R.id.connectButton)
        scanButton = findViewById(R.id.scanButton)
        serverCountText = findViewById(R.id.serverCountText)

        vpnViewModel = VpnViewModel(application)

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

        lifecycleScope.launch {
            vpnViewModel.startServerScanner()
        }
    }
}
