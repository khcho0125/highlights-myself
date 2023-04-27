package com.liner.config

import com.liner.api.includeCollection
import com.liner.api.includeHighlight
import com.liner.api.includeUser
import io.ktor.server.application.Application

fun Application.configureInject() {
    includeCollection()
    includeHighlight()
    includeUser()
}
