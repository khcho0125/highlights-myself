package com.liner.config

import com.liner.config.exception.DomainException
import com.liner.config.exception.ErrorResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureHandling() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when (cause) {
                is DomainException -> call.respond(
                    message = ErrorResponse(cause),
                    status = getHttpStatus(cause)
                )
                else -> call.respond(
                    message = DomainException.InternalError(),
                    status = HttpStatusCode.InternalServerError
                )
            }
        }
    }
}

private fun getHttpStatus(exception: DomainException): HttpStatusCode = when(exception) {
    is DomainException.BadRequest -> HttpStatusCode.BadRequest
    is DomainException.NotFound -> HttpStatusCode.NotFound
    is DomainException.Conflict -> HttpStatusCode.Conflict
    is DomainException.InternalError -> HttpStatusCode.InternalServerError
}
