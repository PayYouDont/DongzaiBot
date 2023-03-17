package com.payudon.command

import com.payudon.config.HelpConfig
import com.payudon.util.htmlToImage
import net.mamoe.mirai.event.events.MessageEvent


class GenshinCommand {
    private val commands = mutableListOf<Command>()

    init {
        commands.add(HelpCommand())
    }

    //回复消息
    suspend fun reply(msg: String, event: MessageEvent) {
        commands.firstOrNull {
            it.rules.firstOrNull { rule -> msg.matches(Regex(rule)) } != null
        }?.apply {
            reply(msg, event)
        }
    }
}

suspend fun main() {
    var headImg = "可莉"
    var headStyle =
        "<style> .head_box { background: url(../../img/namecard/$headImg.png) #fff; background-position-x: 42px; background-repeat: no-repeat; background-size: auto 101%; }</style>"
    var map = mutableMapOf<String, Any>()
    map["headStyle"] = headStyle
    map["version"] = "1.0.0"
    map["groups"] = mutableListOf<HelpConfig.Group>()
    htmlToImage(
        null,
        "D:\\IDEA-WORKSPACE\\dongzaiBot\\debug-sandbox\\data\\com.payudon.bot",
        "help",
        map,
        "192.168.1.169:8080",
        "192.168.1.161:8090/test"
    )?.also {
        //event.sender.sendMessage(it)
    }
}

