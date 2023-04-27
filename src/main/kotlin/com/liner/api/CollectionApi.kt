package com.liner.api

import io.ktor.server.application.Application
import io.ktor.server.routing.route
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

class CollectionApi : Api({
    route("/collection") {
    }
})

fun Application.includeCollection() = koin {
    module {
        singleOf(::CollectionApi) bind Api::class
    }
}
