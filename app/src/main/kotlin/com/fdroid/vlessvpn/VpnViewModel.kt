package com.fdroid.vlessvpn

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VpnViewModel(application: Application) : AndroidViewModel(application) {

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean> = _isConnected

    private val _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> = _statusMessage

    private val _serverCount = MutableLiveData<Int>()
    val serverCount: LiveData<Int> = _serverCount

    private val serverScanner = ServerScanner()
    private var scannerJob: Job? = null

    init {
        _statusMessage.value = "Ready"
        loadServers()
    }

    private fun loadServers() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val servers = serverScanner.scanAllSources()
                _serverCount.postValue(servers.size)
                _statusMessage.postValue("Loaded ${servers.size} servers")
            } catch (e: Exception) {
                _statusMessage.postValue("Error: ${e.message}")
            }
        }
    }

    fun connect() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _statusMessage.postValue("Connecting...")
                delay(2000)
                _isConnected.postValue(true)
                _statusMessage.postValue("Connected")
            } catch (e: Exception) {
                _statusMessage.postValue("Connection failed: ${e.message}")
            }
        }
    }

    fun disconnect() {
        CoroutineScope(Dispatchers.IO).launch {
            _isConnected.postValue(false)
            _statusMessage.postValue("Disconnected")
        }
    }

    fun scanServers() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _statusMessage.postValue("Scanning...")
                val servers = serverScanner.scanAllSources()
                _serverCount.postValue(servers.size)
                _statusMessage.postValue("Found ${servers.size} servers")
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
                delay(3600000)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scannerJob?.cancel()
    }
}
