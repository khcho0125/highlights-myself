package com.liner.persistence

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(
    context = Dispatchers.IO
) {
    block()
}
