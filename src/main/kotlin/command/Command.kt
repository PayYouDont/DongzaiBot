package com.payudon.command

import net.mamoe.mirai.event.events.MessageEvent

abstract class Command(
    //命令名称
    val name: String?,
    //插件描述
    val dsc: String?,
    //命令规则
    val rules: MutableList<String>,
    //监听事件 为null时监听所有事件,不为null时监听指定事件
    val messageEvent: MessageEvent? = null
) {
    abstract suspend fun reply(msg: String, event: MessageEvent)
}