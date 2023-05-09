package com.liner.application.highlight.usecase

import com.liner.config.exception.CollectionException
import com.liner.config.exception.UserException
import com.liner.persistence.Transaction.dbQuery
import com.liner.persistence.collection.repository.CollectionRepository
import com.liner.persistence.highlight.repository.HighlightRepository
import com.liner.persistence.user.repository.UserRepository
import kotlinx.serialization.Serializable

class GetHighlight(
    private val collectionRepository: CollectionRepository,
    private val highlightRepository: HighlightRepository,
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(userId: Int, cursorCollectionId: Int?, size: Int): Response = dbQuery {
        if (userRepository.existsById(userId)) {
            throw UserException.NotFound()
        }

        cursorCollectionId?.let {
            if (collectionRepository.existsByIdAndUserId(it, userId)) {
                throw CollectionException.NotFound()
            }
        }

        val highlights = highlightRepository.findAllByCollectionIdWithPagination(
            collectionId = cursorCollectionId,
            size = size,
        )

        return@dbQuery highlights
            .map { HighlightView(it.id, it.content) }
            .let(::Response)
    }

    @Serializable
    data class Response(
        val highlights: List<HighlightView>,
    )

    @Serializable
    data class HighlightView(
        val id: Int,
        val content: String,
    )
}
