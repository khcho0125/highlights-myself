package com.liner.persistence.highlight.factory

import com.liner.domain.highlight.HighlightStorage
import com.liner.persistence.highlight.entity.HighlightStorageTable
import com.liner.persistence.highlight.repository.HighlightStorageRepository
import org.jetbrains.exposed.sql.batchInsert

class HighlightStorageQueryFactory : HighlightStorageRepository {

    override suspend fun insertAll(storages: List<HighlightStorage>) {
        HighlightStorageTable.batchInsert(storages) {
            this[HighlightStorageTable.highlightId] = it.highlightId
            this[HighlightStorageTable.collectionId] = it.collectionId
        }
    }
}