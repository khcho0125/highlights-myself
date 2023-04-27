package com.liner.config.exception

object HighlightException {

    class NotFound(override val message: String? = null) :
        DomainException.NotFound(message, HighlightErrorCode.NOT_FOUND)
}

enum class HighlightErrorCode(
    override val sequence: Int,
    override val defaultMessage: String
) : ErrorCode {

    NOT_FOUND(1, "Highlight Not Found")

    ;

    override val header: String = "LIGHT"
}
