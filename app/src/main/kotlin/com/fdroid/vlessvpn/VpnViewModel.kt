package com.fdroid.vlessvpn

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * VLESS VPN ViewModel
 * Manages VPN connection state and server scanning
 */
class VpnViewModel(application: Application) : AndroidViewModel(application) {

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean> = _isConnected

    private val _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> = _statusMessage

    private val _serverCount = MutableLiveData<Int>()
    val serverCount: LiveData<Int> = _serverCount

    private val _servers = MutableLiveData<List<VlessServer>>()
    val servers: LiveData<List<VlessServer>> = _servers

    private val _dpiStats = MutableLiveData<DpiStats>()
    val dpiStats: LiveData<DpiStats> = _dpiStats

    private var scannerJob: Job? = null
    private val serverScanner = ServerScanner()

    private val connectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _statusMessage.postValue("Network available")
        }

        override fun onLost(network: Network) {
            _statusMessage.postValue("Network lost")
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            val hasInternet = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            _isConnected.postValue(hasInternet)
        }
    }

    init {
        _statusMessage.value = "Ready"
        _dpiStats.value = DpiStats()
        registerNetworkCallback()
    }

    private fun registerNetworkCallback() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    fun connect() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _statusMessage.postValue("Connecting...")

                val bestServer = servers.value?.firstOrNull { it.isWorking }
                    ?: VlessServer(
                        host = "81.200.149.222",
                        port = 443,
                        uuid = "1c1d7040-828e-4fcc-8406-d8545a30c2ff",
                        country = "🌍"
                    )

                // Start VPN service
                VpnService.start(getApplication(), bestServer)

                delay(2000)
                _isConnected.postValue(true)
                _statusMessage.postValue("Connected with DPI Bypass")

                val stats = _dpiStats.value?.copy(bypassesSuccessful = 1)
                _dpiStats.postValue(stats)

            } catch (e: Exception) {
                _statusMessage.postValue("Connection failed: ${e.message}")
                val stats = _dpiStats.value?.copy(blocksDetected = 1)
                _dpiStats.postValue(stats)
            }
        }
    }

    fun disconnect() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                VpnService.stop(getApplication())
                _isConnected.postValue(false)
                _statusMessage.postValue("Disconnected")
            } catch (e: Exception) {
                _statusMessage.postValue("Error: ${e.message}")
            }
        }
    }

    fun scanServers() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _statusMessage.postValue("Scanning servers...")

                val scannedServers = serverScanner.scanAllSources()
                _servers.postValue(scannedServers)
                _serverCount.postValue(scannedServers.size)

                val workingCount = scannedServers.count { it.isWorking }
                _statusMessage.postValue("Found $workingCount working servers")

            } catch (e: Exception) {
                _statusMessage.postValue("Scan failed: ${e.message}")
            }
        }
    }

    suspend fun startServerScanner() {
        scannerJob?.cancel()
        scannerJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                scanServers()
                delay(3600000) // Scan every hour
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scannerJob?.cancel()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}

/**
 * DPI Bypass Statistics
 */
data class DpiStats(
    val blocksDetected: Int = 0,
    val bypassesSuccessful: Int = 0,
    val strategySwitches: Int = 0,
    val currentStrategy: String = "fragmentation"
)
