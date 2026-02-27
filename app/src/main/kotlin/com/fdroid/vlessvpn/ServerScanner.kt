package com.fdroid.vlessvpn

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern

/**
 * Server Scanner - F-Droid compatible
 * Scans VLESS servers from public sources
 * No proprietary dependencies
 */
class ServerScanner {

    private val sources = listOf(
        "https://raw.githubusercontent.com/igareck/vpn-configs-for-russia/main/WHITE-CIDR-RU-all.txt",
        "https://raw.githubusercontent.com/igareck/vpn-configs-for-russia/main/BLACK_VLESS_RUS.txt",
        "https://raw.githubusercontent.com/igareck/vpn-configs-for-russia/main/Vless-Reality-White-Lists-Rus-Mobile.txt"
    )

    private val countryFlags = mapOf(
        "germany" to "🇩🇪", "de " to "🇩🇪",
        "usa" to "🇺🇸", "us " to "🇺🇸",
        "netherlands" to "🇳🇱", "nl " to "🇳🇱",
        "france" to "🇫🇷", "fr " to "🇫🇷",
        "uk" to "🇬🇧", "gb " to "🇬🇧",
        "finland" to "🇫🇮", "fi " to "🇫🇮",
        "poland" to "🇵🇱", "pl " to "🇵🇱",
        "latvia" to "🇱🇻", "lv " to "🇱🇻"
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

        // Remove duplicates
        allServers.distinctBy { "${it.host}:${it.port}" }
            .take(200) // Limit for performance
    }

    private fun fetchSource(urlString: String): List<VlessServer> {
        val servers = mutableListOf<VlessServer>()

        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 15000
            connection.readTimeout = 15000
            connection.requestMethod = "GET"
            connection.setRequestProperty("User-Agent", "Mozilla/5.0")

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val content = reader.readText()
                reader.close()

                servers.addAll(parseContent(content, urlString))
            }

            connection.disconnect()
        } catch (e: Exception) {
            // Handle error silently
        }

        return servers
    }

    private fun parseContent(content: String, source: String): List<VlessServer> {
        val servers = mutableListOf<VlessServer>()

        content.lines().forEach { line ->
            val trimmedLine = line.trim()
            if (trimmedLine.startsWith("vless://")) {
                parseVlessUrl(trimmedLine, source)?.let { server ->
                    servers.add(server)
                }
            }
        }

        return servers
    }

    private fun parseVlessUrl(url: String, source: String): VlessServer? {
        return try {
            val withoutProtocol = url.replace("vless://", "")

            // Extract name
            var name = ""
            var urlWithoutName = withoutProtocol
            if (withoutProtocol.contains("#")) {
                val parts = withoutProtocol.split("#", limit = 2)
                urlWithoutName = parts[0]
                name = java.net.URLDecoder.decode(parts[1], "UTF-8")
            }

            // Extract UUID and host:port
            val atParts = urlWithoutName.split("@", limit = 2)
            if (atParts.size != 2) return null

            val uuid = atParts[0]
            val hostPort = atParts[1]

            // Parse host:port
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

            // Parse parameters
            val params = if (portEndIndex != -1) {
                hostPort.substring(portEndIndex + 1)
            } else {
                ""
            }

            val paramsMap = parseParams(params)

            VlessServer(
                host = host,
                port = port,
                uuid = uuid,
                security = paramsMap["security"] ?: "reality",
                sni = paramsMap["sni"] ?: "",
                pbk = paramsMap["pbk"] ?: "",
                sid = paramsMap["sid"] ?: "",
                fp = paramsMap["fp"] ?: "chrome",
                flow = paramsMap["flow"] ?: "xtls-rprx-vision",
                country = detectCountry(name),
                name = name,
                source = source
            )
        } catch (e: Exception) {
            null
        }
    }

    private fun parseParams(params: String): Map<String, String> {
        return params.split('&').associateNotNull { param ->
            val eqIndex = param.indexOf('=')
            if (eqIndex == -1) return@associateNotNull null
            val key = param.substring(0, eqIndex)
            val value = java.net.URLDecoder.decode(param.substring(eqIndex + 1), "UTF-8")
            key to value
        }
    }

    private fun <K, V> Sequence<Pair<K, V>>.associateNotNull(): Map<K, V> {
        return filter { it.first != null && it.second != null }.toMap()
    }

    private fun detectCountry(name: String): String {
        val nameLower = name.lowercase()
        for ((keyword, flag) in countryFlags) {
            if (nameLower.contains(keyword)) {
                return flag
            }
        }
        return "🌍"
    }

    suspend fun checkServer(server: VlessServer): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            val startTime = System.currentTimeMillis()

            val socket = java.net.Socket()
            socket.connect(java.net.InetSocketAddress(server.host, server.port), 5000)
            socket.close()

            val latency = System.currentTimeMillis() - startTime
            server.latency = latency.toInt()
            server.isWorking = true
            server.checkedAt = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault())
                .format(java.util.Date())

            true
        } catch (e: Exception) {
            server.isWorking = false
            false
        }
    }
}

// Extension function for filtering nulls
inline fun <T, K, V> Iterable<T>.mapNotNull(transform: (T) -> Pair<K, V>?): Map<K, V> {
    return mapNotNull(transform).toMap()
}

inline fun <K, V> Sequence<Pair<K, V>>.associateNotNull(): Map<K, V> {
    return filter { it.first != null && it.second != null }.toMap()
}
