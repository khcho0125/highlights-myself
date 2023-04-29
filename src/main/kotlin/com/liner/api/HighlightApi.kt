package com.liner.api

import com.liner.application.highlight.usecase.SaveHighlight
import com.liner.application.highlight.usecase.StoreInCollection
import com.liner.config.exception.DomainException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

class HighlightApi(
    saveHighlight: SaveHighlight,
    storeHighlight: StoreInCollection
) : Api({
    route("/highlight") {
        post("/{user-id}") {
            val request: SaveHighlight.Request = call.receive()
            val userId: Int = call.parameters["user-id"]?.toInt()
                ?: throw DomainException.BadRequest("Require User ID")

                call.respond(
                message = saveHighlight(request, userId),
                status = HttpStatusCode.Created
            )
        }

        post("/{highlight-id}/collections") {
            val highlightId: Int = call.parameters["highlight-id"]?.toInt()
                ?: throw DomainException.BadRequest("Require Highlight ID")

            val request: StoreInCollection.Request = call.receive()

            call.respond(
                message = storeHighlight(
                    request = request,
                    highlightId = highlightId
                )
            )
        }
    }
})

fun KoinApplication.includeHighlight(): Module = module {
    singleOf(::SaveHighlight)
    singleOf(::StoreInCollection)
    singleOf(::HighlightApi) bind Api::class
}
