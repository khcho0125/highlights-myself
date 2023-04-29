package com.liner.application.highlight.usecase

import com.liner.config.exception.UserException
import com.liner.domain.highlight.Highlight
import com.liner.persistence.dbQuery
import com.liner.persistence.highlight.repository.HighlightRepository
import com.liner.persistence.user.repository.UserRepository
import kotlinx.serialization.Serializable

class SaveHighlight(
    private val highlightRepository: HighlightRepository,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(request: Request, userId: Int): Response = dbQuery {

        // 유저 ID 유효성 검사
        if(userRepository.existsById(userId).not()) {
            throw UserException.NotFound()
        }

        val highlightId: Int = highlightRepository.insert(
            Highlight(
                content = request.content,
                userId = userId
            )
        )

        return@dbQuery Response(highlightId)
    }

    @Serializable
    data class Request(
        val content: String
    )

    @Serializable
    data class Response(
        val highlightId: Int,
    )
}