package com.liner.application.highlight.usecase

import com.liner.config.exception.CollectionException
import com.liner.config.exception.HighlightException
import com.liner.domain.highlight.HighlightStorage
import com.liner.persistence.collection.repository.CollectionRepository
import com.liner.persistence.dbQuery
import com.liner.persistence.highlight.repository.HighlightRepository
import com.liner.persistence.highlight.repository.HighlightStorageRepository
import kotlinx.serialization.Serializable

class StoreInCollection(
    private val collectionRepository: CollectionRepository,
    private val highlightStorageRepository: HighlightStorageRepository,
    private val highlightRepository: HighlightRepository
) {

    suspend operator fun invoke(request: Request, highlightId: Int) : Unit = dbQuery {
         if(highlightRepository.existsById(highlightId)) {
             throw HighlightException.NotFound()
         }

        if(request.collectionIds.all { collectionRepository.existsByIdAndUserId(it, request.userId) }) {
            throw CollectionException.NotFound()
        }

        val highlightStorages: List<HighlightStorage> = request.collectionIds.map {
            HighlightStorage(
                highlightId = highlightId,
                collectionId = it
            )
        }

        highlightStorageRepository.insertAll(highlightStorages)
    }

    @Serializable
    data class Request(
        val userId: Int,
        val collectionIds: List<Int>
    )
}