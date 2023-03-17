package com.payudon.command

import com.payudon.Dongzai
import net.mamoe.mirai.console.command.CommandContext
import net.mamoe.mirai.console.command.RawCommand
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.message.data.MessageChain

object TestCommand: SimpleCommand(Dongzai,"#"){
    @Handler
    suspend fun test(context: CommandContext):String{
        println("11111111")
        return "hello"
    }
}