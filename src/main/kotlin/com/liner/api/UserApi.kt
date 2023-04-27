package com.liner.api

import com.liner.application.user.usecase.CreateUser
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
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

class UserApi(
    createUser: CreateUser
) : Api({
    route("/user") {
        post {
            call.respond(
                message = createUser(),
                status = HttpStatusCode.Created
            )
        }
    }
})

fun KoinApplication.includeUser(): Module = module {
    singleOf(::CreateUser)
    singleOf(::UserApi) bind Api::class
}
