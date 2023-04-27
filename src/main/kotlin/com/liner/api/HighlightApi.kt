package com.liner.api

import io.ktor.server.application.Application
import io.ktor.server.routing.route
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

class HighlightApi : Api({
    route("/highlight") {
    }
})

fun Application.includeHighlight() = koin {
    module {
        singleOf(::HighlightApi) bind Api::class
    }
}
