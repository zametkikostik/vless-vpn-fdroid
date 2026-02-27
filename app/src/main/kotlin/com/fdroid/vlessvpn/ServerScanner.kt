package com.fdroid.vlessvpn

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class VlessServer(
    val host: String,
    val port: Int,
    val uuid: String,
    val security: String = "reality",
    val sni: String = "",
    val pbk: String = "",
    val sid: String = "",
    val country: String = "🌍",
    val name: String = "",
    var latency: Int = 9999,
    var isWorking: Boolean = false,
    val source: String = "scanner",
    val checkedAt: String = ""
)

class ServerScanner {

    private val sources = listOf(
        "https://raw.githubusercontent.com/igareck/vpn-configs-for-russia/main/WHITE-CIDR-RU-all.txt",
        "https://raw.githubusercontent.com/igareck/vpn-configs-for-russia/main/BLACK_VLESS_RUS.txt"
    )

    private val countryFlags = mapOf(
        "germany" to "🇩🇪", "usa" to "🇺🇸", "netherlands" to "🇳🇱",
        "france" to "🇫🇷", "uk" to "🇬🇧", "finland" to "🇫🇮",
        "poland" to "🇵🇱", "latvia" to "🇱🇻"
    )

    suspend fun scanAllSources(): List<VlessServer> = withContext(Dispatchers.IO) {
        val allServers = mutableListOf<VlessServer>()

        sources.forEach { source ->
            try {
                val servers = fetchSource(source)
                allServers.addAll(servers)
            } catch (e: Exception) {
                // Skip failed sources
            }
        }

        allServers.distinctBy { "${it.host}:${it.port}" }.take(200)
    }

    private fun fetchSource(urlString: String): List<VlessServer> {
        val servers = mutableListOf<VlessServer>()
        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 15000
            connection.readTimeout = 15000

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val content = reader.readText()
                reader.close()
                servers.addAll(parseContent(content, urlString))
            }
            connection.disconnect()
        } catch (e: Exception) {
            // Handle error
        }
        return servers
    }

    private fun parseContent(content: String, source: String): List<VlessServer> {
        val servers = mutableListOf<VlessServer>()
        content.lines().forEach { line ->
            val trimmedLine = line.trim()
            if (trimmedLine.startsWith("vless://")) {
                parseVlessUrl(trimmedLine, source)?.let { servers.add(it) }
            }
        }
        return servers
    }

    private fun parseVlessUrl(url: String, source: String): VlessServer? {
        return try {
            val withoutProtocol = url.replace("vless://", "")
            var name = ""
            var urlWithoutName = withoutProtocol
            
            if (withoutProtocol.contains("#")) {
                val parts = withoutProtocol.split("#", limit = 2)
                urlWithoutName = parts[0]
                name = java.net.URLDecoder.decode(parts[1], "UTF-8")
            }

            val atParts = urlWithoutName.split("@", limit = 2)
            if (atParts.size != 2) return null

            val uuid = atParts[0]
            val hostPort = atParts[1]

            val colonIndex = hostPort.indexOf(':')
            if (colonIndex == -1) return null

            val host = hostPort.substring(0, colonIndex)
            val portEndIndex = hostPort.indexOf('?', colonIndex)
            val portStr = if (portEndIndex != -1) {
                hostPort.substring(colonIndex + 1, portEndIndex)
            } else {
                hostPort.substring(colonIndex + 1)
            }

            val port = portStr.toIntOrNull() ?: return null
            VlessServer(host = host, port = port, uuid = uuid, country = detectCountry(name), name = name, source = source)
        } catch (e: Exception) {
            null
        }
    }

    private fun detectCountry(name: String): String {
        val nameLower = name.lowercase()
        for ((keyword, flag) in countryFlags) {
            if (nameLower.contains(keyword)) return flag
        }
        return "🌍"
    }

    suspend fun checkServer(server: VlessServer): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            val startTime = System.currentTimeMillis()
            val socket = java.net.Socket()
            socket.connect(java.net.InetSocketAddress(server.host, server.port), 5000)
            socket.close()
            server.latency = (System.currentTimeMillis() - startTime).toInt()
            server.isWorking = true
            server.checkedAt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            true
        } catch (e: Exception) {
            server.isWorking = false
            false
        }
    }
}
