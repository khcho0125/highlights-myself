package com.liner.config.exception

object CollectionException {

    class NotFound(override val message: String? = null) :
        DomainException.NotFound(message, CollectionErrorCode.NOT_FOUND)
}

enum class CollectionErrorCode(
    override val sequence: Int,
    override val defaultMessage: String
) : ErrorCode {

    NOT_FOUND(1, "Collection Not Found")

    ;

    override val header: String = "COLLECT"

}