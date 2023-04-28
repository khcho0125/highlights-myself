package com.liner.domain.highlight

data class Highlight(
    val id: Int = 0,
    val content: String
) {

    companion object {
        fun new(content: String): Highlight = Highlight(
            content = content
        )

        const val CONTENT_LENGTH_LIMIT: Int = 255
    }
}
