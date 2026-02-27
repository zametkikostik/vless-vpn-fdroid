package com.fdroid.vlessvpn

import java.io.Serializable

/**
 * VLESS Server data model
 * F-Droid compatible - no proprietary dependencies
 */
data class VlessServer(
    val host: String,
    val port: Int,
    val uuid: String,
    val protocol: String = "vless",
    val security: String = "reality",
    val sni: String = "",
    val pbk: String = "",
    val sid: String = "",
    val fp: String = "chrome",
    val flow: String = "xtls-rprx-vision",
    val country: String = "🌍",
    val name: String = "",
    var latency: Int = 9999,
    var isWorking: Boolean = false,
    val source: String = "scanner",
    val checkedAt: String = ""
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }

    fun toVlessUrl(): String {
        val params = buildString {
            append("security=$security")
            if (sni.isNotEmpty()) append("&sni=$sni")
            if (pbk.isNotEmpty()) append("&pbk=$pbk")
            if (sid.isNotEmpty()) append("&sid=$sid")
            append("&fp=$fp")
            if (flow.isNotEmpty()) append("&flow=$flow")
        }
        return "vless://$uuid@$host:$port?$params#$name"
    }

    fun isValid(): Boolean {
        return host.isNotEmpty() &&
                port in 1..65535 &&
                uuid.isNotEmpty() &&
                uuid.length == 36 // UUID format
    }
}
