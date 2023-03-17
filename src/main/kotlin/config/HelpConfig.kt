package com.payudon.config

import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

class HelpConfig : AutoSavePluginConfig("help") {
    @Serializable
    data class Group(
        val group: String,
        val list: MutableList<List>
    )

    @Serializable
    data class List(
        val icon: String,
        val title: String,
        val desc: String,
    )

    val groups: MutableList<Group> by value()
}
