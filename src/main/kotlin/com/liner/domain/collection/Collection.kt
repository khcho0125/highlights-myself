package com.liner.domain.collection

data class Collection(
    val id: Int = 0,
    val userId: Int,
    val name: String,
    val parentId: Int?
) {

    companion object {
        const val NAME_LENGTH_LIMIT: Int = 25
    }
}
