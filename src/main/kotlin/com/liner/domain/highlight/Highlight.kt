package com.liner.domain.highlight

data class Highlight(
    val id: Int,
    val content: String,
    val groupList: List<Int>
) {

    companion object {
        const val CONTENT_LENGTH_LIMIT: Int = 255
    }
}
