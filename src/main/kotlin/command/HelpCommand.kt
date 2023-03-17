package com.payudon.command

import com.payudon.Dongzai.reload
import com.payudon.config.HelpConfig
import com.payudon.util.htmlToImage
import net.mamoe.mirai.console.plugin.PluginManager
import net.mamoe.mirai.event.events.MessageEvent

class HelpCommand : Command(
    "help",
    null,
    mutableListOf("^(#|云崽)*(命令|帮助|菜单|help|说明|功能|指令|使用说明)\$"),
    null
) {
    override suspend fun reply(msg: String, event: MessageEvent) {
        val help = HelpConfig()
        help.reload()
        val map = mutableMapOf<String, Any>()
        val headImg = "可莉"
        val headStyle =
            "<style> .head_box { background: url(../../img/namecard/$headImg.png) #fff; background-position-x: 42px; background-repeat: no-repeat; background-size: auto 101%; }</style>"
        map["headStyle"] = headStyle
        map["version"] = "1.0.0"
        map["groups"] = help.groups
        htmlToImage(
            event.sender,
            PluginManager.pluginsDataFolder.path,
            "help",
            map,
            "192.168.1.169:8080",
            "192.168.1.161:8090/test"
        )?.also {
            event.sender.sendMessage(it)
        }
    }
}