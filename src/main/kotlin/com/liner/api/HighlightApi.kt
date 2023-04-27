package com.liner.api

import com.liner.application.highlight.usecase.SaveHighlight
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

class HighlightApi(
    saveHighlight: SaveHighlight
) : Api({
    route("/highlight") {
        post {
            val request: SaveHighlight.Request = call.receive()

            call.respond(
                message = saveHighlight(request),
                status = HttpStatusCode.Created
            )
        }
    }
})

fun KoinApplication.includeHighlight(): Module = module {
    singleOf(::SaveHighlight)
    singleOf(::HighlightApi) bind Api::class
}
