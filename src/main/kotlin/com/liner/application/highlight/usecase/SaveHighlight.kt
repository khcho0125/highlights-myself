package com.liner.application.highlight.usecase

import com.liner.domain.highlight.Highlight
import com.liner.persistence.dbQuery
import com.liner.persistence.highlight.repository.HighlightRepository
import kotlinx.serialization.Serializable

class SaveHighlight(
    private val highlightRepository: HighlightRepository
) {

    suspend operator fun invoke(request: Request) : Response = dbQuery {
        val highlightId: Int = highlightRepository.insert(
            Highlight(content = request.content)
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