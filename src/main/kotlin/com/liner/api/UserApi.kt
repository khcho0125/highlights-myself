package com.liner.api

import com.liner.application.user.usecase.CreateUser
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

class UserApi(
    createUser: CreateUser
) : Api({
    route("/user") {
        post {
            val request: CreateUser.Request = call.receive()

            createUser(request)

            call.response.status(HttpStatusCode.Created)
        }
    }
})

fun Application.includeUser() = koin {
    module {
        singleOf(::CreateUser)
        singleOf(::UserApi) bind Api::class
    }
}
