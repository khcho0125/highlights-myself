package com.liner.persistence.highlight.factory

import com.liner.domain.highlight.HighlightStorage
import com.liner.persistence.highlight.entity.HighlightStorageTable
import com.liner.persistence.highlight.repository.HighlightStorageRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.select

class HighlightStorageQueryFactory : HighlightStorageRepository {

    private fun toDomain(row: ResultRow): HighlightStorage = HighlightStorage(
        highlightId = row[HighlightStorageTable.highlightId].value,
        collectionId = row[HighlightStorageTable.collectionId].value,
    )

    override suspend fun insertAll(storages: List<HighlightStorage>) {
        HighlightStorageTable.batchInsert(storages) {
            this[HighlightStorageTable.highlightId] = it.highlightId
            this[HighlightStorageTable.collectionId] = it.collectionId
        }
    }

    override suspend fun findAllByCollectionIds(collectionIds: List<Int>): List<HighlightStorage> {
        return HighlightStorageTable
            .select { HighlightStorageTable.collectionId inList collectionIds }
            .map(::toDomain)
    }
}
