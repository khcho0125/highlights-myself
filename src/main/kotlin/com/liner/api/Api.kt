package com.liner.api

import io.ktor.server.application.Application
import io.ktor.server.routing.Routing
import io.ktor.server.routing.routing

open class Api(private val block: Routing.() -> Unit) {
    operator fun invoke(application: Application): Routing = application.routing(block)
}
