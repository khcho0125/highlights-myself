package com.liner.domain.highlight

data class Highlight(
    val id: Int = 0,
    val content: String,
    val userId: Int
) {

    companion object {
        const val CONTENT_LENGTH_LIMIT: Int = 255
    }
}
