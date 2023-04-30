package com.liner.domain.collection

import com.liner.config.exception.CollectionException

data class Collection(
    val id: Int = 0,
    val userId: Int,
    val name: String,
    val parentId: Int?,
) {

    init {
        if (name.length > NAME_LENGTH_LIMIT) {
            throw CollectionException.OutOfLengthName()
        }
    }

    companion object {
        const val NAME_LENGTH_LIMIT: Int = 25
    }
}
