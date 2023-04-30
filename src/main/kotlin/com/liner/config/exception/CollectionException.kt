package com.liner.config.exception

import com.liner.domain.collection.Collection

object CollectionException {

    class NotFound(override val message: String? = null) :
        DomainException.NotFound(message, CollectionErrorCode.NOT_FOUND)

    class DuplicatedName(override val message: String? = null) :
        DomainException.Conflict(message, CollectionErrorCode.DUPLICATED_NAME)

    class OutOfLengthName(override val message: String? = null) :
        DomainException.BadRequest(message, CollectionErrorCode.OUT_OF_LENGTH_NAME)
}

enum class CollectionErrorCode(
    override val sequence: Int,
    override val defaultMessage: String,
) : ErrorCode {

    NOT_FOUND(1, "Collection Not Found"),
    DUPLICATED_NAME(2, "You have a collection with the same name"),
    OUT_OF_LENGTH_NAME(3, "Collection Name is over ${Collection.NAME_LENGTH_LIMIT} letters"),

    ;

    override val header: String = "COLLECT"
}
