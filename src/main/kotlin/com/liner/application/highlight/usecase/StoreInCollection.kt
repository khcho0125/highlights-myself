package com.liner.application.highlight.usecase

import com.liner.config.exception.CollectionException
import com.liner.config.exception.HighlightException
import com.liner.config.exception.UserException
import com.liner.domain.highlight.HighlightStorage
import com.liner.persistence.collection.repository.CollectionRepository
import com.liner.persistence.dbQuery
import com.liner.persistence.highlight.repository.HighlightRepository
import com.liner.persistence.highlight.repository.HighlightStorageRepository
import com.liner.persistence.user.repository.UserRepository
import kotlinx.serialization.Serializable

class StoreInCollection(
    private val collectionRepository: CollectionRepository,
    private val highlightStorageRepository: HighlightStorageRepository,
    private val highlightRepository: HighlightRepository,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(request: Request, highlightId: Int): Unit = dbQuery {

        // 유저 ID 유효성 검사
        if(userRepository.existsById(request.userId).not()) {
            throw UserException.NotFound()
        }

        // 하이라이트 여부 검사
         if(highlightRepository.existsById(highlightId)) {
             throw HighlightException.NotFound()
         }

        // 컬렉션 ID 리스트 유효성 검사
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