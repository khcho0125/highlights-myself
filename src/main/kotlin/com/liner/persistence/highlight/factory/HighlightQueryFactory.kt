package com.liner.persistence.highlight.factory

import com.liner.domain.highlight.Highlight
import com.liner.persistence.highlight.entity.HighlightStorageTable
import com.liner.persistence.highlight.entity.HighlightTable
import com.liner.persistence.highlight.repository.HighlightRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select

class HighlightQueryFactory : HighlightRepository {
    private fun toDomain(row: ResultRow): Highlight = Highlight(
        id = row[HighlightTable.id].value,
        content = row[HighlightTable.content],
        userId = row[HighlightTable.userId].value,
    )

    override suspend fun existsById(id: Int): Boolean {
        return HighlightTable
            .select { HighlightTable.id eq id }
            .limit(1)
            .empty().not()
    }

    override suspend fun findById(id: Int): Highlight? {
        return HighlightTable
            .select { HighlightTable.id eq id }
            .singleOrNull()
            ?.let(::toDomain)
    }

    override suspend fun findAllByCollectionIdWithPagination(collectionId: Int?, size: Int): List<Highlight> {
        return (HighlightStorageTable rightJoin HighlightTable)
            .select { HighlightStorageTable.collectionId eq collectionId }
            .limit(size)
            .map(::toDomain)
    }

    override suspend fun insert(highlight: Highlight): Int {
        return HighlightTable.insertAndGetId {
            it[content] = highlight.content
            it[userId] = highlight.userId
        }.value
    }
}
