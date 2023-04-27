package com.liner.domain.highlight

data class Highlight(
    val id: Int = 0,
    val content: String,
    val groupList: List<Int>
) {

    companion object {
        fun new(content: String): Highlight = Highlight(
            content = content,
            groupList = listOf()
        )

        const val CONTENT_LENGTH_LIMIT: Int = 255
    }
}
