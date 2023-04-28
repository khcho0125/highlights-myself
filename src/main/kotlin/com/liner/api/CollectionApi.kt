package com.liner.api

import com.liner.application.collection.usecase.CreateCollection
import com.liner.config.exception.DomainException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

class CollectionApi(
    createCollection: CreateCollection
) : Api({
    route("/collection") {
        post("/{user-id}") {
            val userId: Int = call.parameters["user-id"]?.toInt()
                ?: throw DomainException.BadRequest("Require User ID")

            val request: CreateCollection.Request = call.receive()

            call.respond(
                message = createCollection(request, userId),
                status = HttpStatusCode.Created
            )
        }
    }
})

fun KoinApplication.includeCollection(): Module = module {
    singleOf(::CreateCollection)
    singleOf(::CollectionApi) bind Api::class
}
