package com.liner.persistence.highlight.repository

import com.liner.domain.highlight.HighlightStorage

interface HighlightStorageRepository {

    suspend fun insertAll(storages: List<HighlightStorage>)
}