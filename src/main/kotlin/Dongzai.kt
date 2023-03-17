package com.payudon

import com.payudon.command.GenshinCommand
import net.mamoe.mirai.console.plugin.PluginManager
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.contact.isBotMuted
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.message.data.content
import net.mamoe.mirai.utils.info

object Dongzai : KotlinPlugin(
    JvmPluginDescription(
        id = "com.payudon.bot",
        name = "dongzai bot",
        version = "0.1.0",
    ) {
        author("payudon")
    }
) {
    private val genshin = GenshinCommand()

    override fun onEnable() {
        logger.info { "Plugin loaded" }
        val eventChannel = globalEventChannel()

        println("PluginManager.pluginsDataPath=${PluginManager.pluginsDataPath}")
        //好友消息
        eventChannel.subscribeAlways<FriendMessageEvent> { event ->
            dealMsg(event)
        }
        //群消息
        eventChannel.subscribeAlways<GroupMessageEvent> { event ->
            dealMsg(event)
        }
        //群临时会话消息
        eventChannel.subscribeAlways<GroupTempMessageEvent> { event ->
            dealMsg(event)
        }
        //陌生人消息
        eventChannel.subscribeAlways<StrangerMessageEvent> { event ->
            dealMsg(event)
        }
        //其他客户端消息
        eventChannel.subscribeAlways<OtherClientMessageEvent> { event ->
            dealMsg(event)
        }
        //成员主动离开群: Quit
        eventChannel.subscribeAlways<MemberLeaveEvent.Quit> { event ->
            println("e:$event")
        }
    }

    //处理消息
    private suspend fun dealMsg(event: MessageEvent) {
        if (event is GroupMessageEvent && event.group.isBotMuted) {
            return
        }
        val content = event.message.content
        val text = content.replace("＃", "#").replace("井", "#").trim()
        if (text.startsWith("#")) {
            genshin.reply(text, event)
        }
    }
}