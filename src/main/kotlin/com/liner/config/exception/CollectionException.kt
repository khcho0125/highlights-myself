package com.liner.config.exception

object CollectionException {

    class NotFound(override val message: String? = null) :
        DomainException.NotFound(message, CollectionErrorCode.NOT_FOUND)

    class DuplicatedName(override val message: String? = null) :
        DomainException.Conflict(message, CollectionErrorCode.DUPLICATED_NAME)
}

enum class CollectionErrorCode(
    override val sequence: Int,
    override val defaultMessage: String
) : ErrorCode {

    NOT_FOUND(1, "Collection Not Found"),
    DUPLICATED_NAME(2, "You have a collection with the same name")

    ;

    override val header: String = "COLLECT"

}