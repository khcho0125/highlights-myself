package com.liner.config

import com.liner.api.Api
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.getKoin

fun Application.configureRouting() {
    getKoin().getAll<Api>().forEach {
        it(this)
    }

    routing {
        get {
            call.respondText("Help People Get Smart Faster")
        }
    }
}
