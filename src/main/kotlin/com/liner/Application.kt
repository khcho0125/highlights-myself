package com.liner

import com.liner.config.configureDatabases
import com.liner.config.configureHandling
import com.liner.config.configureInject
import com.liner.config.configureRouting
import com.liner.config.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureInject()
    configureHandling()
    configureSerialization()
    configureDatabases()
    configureRouting()
}
