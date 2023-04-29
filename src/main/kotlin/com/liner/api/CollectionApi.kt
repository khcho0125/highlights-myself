package com.liner.api

import com.liner.application.collection.usecase.CreateCollection
import com.liner.application.collection.usecase.GetCollection
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
    createCollection: CreateCollection,
    getCollection: GetCollection
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

    route("/collections") {
        get("/{user-id}") {
            val cursorCollectionId: Int? = call.request.queryParameters["cursorCollectionId"]?.toInt()

            val size: Int = call.request.queryParameters["size"]?.toInt()
                ?: GET_COLLECTION_DEFAULT_SIZE

            val userId: Int = call.parameters["user-id"]?.toInt()
                ?: throw DomainException.BadRequest("Require User ID")

            call.respond(
                message = getCollection(userId, cursorCollectionId, size),
                status = HttpStatusCode.OK
            )
        }
    }
}) {

    companion object {
        const val GET_COLLECTION_DEFAULT_SIZE: Int = 20
    }
}

fun KoinApplication.includeCollection(): Module = module {
    singleOf(::CreateCollection)
    singleOf(::GetCollection)
    singleOf(::CollectionApi) bind Api::class
}
