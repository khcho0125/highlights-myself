package com.liner.config

import com.liner.persistence.collection.entity.CollectionTable
import com.liner.persistence.highlight.entity.HighlightStorageTable
import com.liner.persistence.highlight.entity.HighlightTable
import com.liner.persistence.user.entity.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.Application
import io.ktor.server.config.ApplicationConfig
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val exposed: Database = Database.connect(
        ExposedDataSource(environment.config),
    )

    transaction(exposed) {
        SchemaUtils.create(
            CollectionTable,
            HighlightTable,
            UserTable,
            HighlightStorageTable,
        )
    }
}

class ExposedDataSource(
    config: ApplicationConfig,
) : HikariDataSource(
    HikariConfig().apply {
        driverClassName = config.property(DRIVER).getString()
        username = config.property(USER).getString()
        password = config.property(PASSWD).getString()
        jdbcUrl = config.property(URL).getString()
    },
) {

    companion object Prefix {
        private const val PATH: String = "exposed.datasource"

        const val USER: String = "$PATH.username"
        const val PASSWD: String = "$PATH.password"
        const val DRIVER: String = "$PATH.driver"
        const val URL: String = "$PATH.url"
    }
}
