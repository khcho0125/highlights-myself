package com.liner.config

import com.liner.config.serializer.LocalDateTimeSerializer
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.time.LocalDateTime

fun Application.configureSerialization() {
    val serializers = SerializersModule {
        contextual(LocalDateTime::class, LocalDateTimeSerializer)
    }

    install(ContentNegotiation) {
        json(
            Json {
                serializersModule = serializers
                prettyPrint = true
            }
        )
    }

    routing {
        get("/json") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}
