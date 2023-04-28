package com.liner.domain.highlight

data class Highlight(
    val id: Int = 0,
    val content: String
) {

    companion object {
        const val CONTENT_LENGTH_LIMIT: Int = 255
    }
}
