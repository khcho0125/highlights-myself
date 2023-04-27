package com.liner.persistence.highlight.factory

import com.liner.domain.highlight.Highlight
import com.liner.persistence.highlight.entity.HighlightTable
import com.liner.persistence.highlight.repository.HighlightRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select

class HighlightQueryFactory : HighlightRepository {
    private fun toDomain(row: ResultRow) : Highlight = Highlight(
        id = row[HighlightTable.id].value,
        content = row[HighlightTable.content],
        storedCollectionId = null // TODO
    )

    override suspend fun findById(id: Int): Highlight? {
        return HighlightTable.select { HighlightTable.id eq id }
            .singleOrNull()?.let(::toDomain)
    }

    override suspend fun insert(highlight: Highlight): Int {
        return HighlightTable.insertAndGetId {
            it[content] = highlight.content
        }.value
    }

}