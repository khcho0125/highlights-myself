package com.liner.config

import com.liner.api.includeCollection
import com.liner.api.includeHighlight
import com.liner.api.includeUser
import com.liner.persistence.highlight.repository.HighlightRepository
import com.liner.persistence.user.factory.UserQueryFactory
import com.liner.persistence.user.repository.UserRepository
import io.ktor.server.application.Application
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun Application.configureInject() {
    startKoin {
        modules(
            includeCollection(),
//            includeHighlight(),
            includeUser(),
            includeFactory()
        )
    }
}

private fun KoinApplication.includeFactory(): Module = module {
    singleOf(::UserQueryFactory) bind UserRepository::class
}
