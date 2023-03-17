package com.payudon.config

import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

class ServerConfig : AutoSavePluginConfig("server") {
    @Serializable
    data class DoctronServer(
        val ip: String,
        val port: String
    )

    @Serializable
    data class Nginx(
        val ip: String,
        val port: String,
        val home: String
    )

    private val server: DoctronServer by value()
    private val nginx: Nginx by value()
}