package com.liner.config

import com.liner.api.Api
import io.ktor.server.application.Application
import org.koin.ktor.ext.getKoin

fun Application.configureRouting() {
    getKoin().getAll<Api>().forEach {
        it(this)
    }
}
