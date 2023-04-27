package com.liner.config.exception

sealed class DomainException(
    open val code: ErrorCode,
    override val message: String?
) : Throwable(message) {

    open class NotFound(
        override val code: ErrorCode = DomainErrorCode.NOT_FOUND,
        override val message: String? = null
    ) : DomainException(code, message)
}

interface ErrorCode {
    val sequence: Int
    val defaultMessage: String

    val header: String

    fun serial(): String = "$-${formatPattern.format(sequence)}"

    companion object {
        const val formatPattern: String = "%03"
    }
}

enum class DomainErrorCode(
    override val sequence: Int,
    override val defaultMessage: String
) : ErrorCode {

    BAD_REQUEST(1, "Bad Request"),
    UNAUTHORIZED(2, "Unauthorized"),
    NOT_FOUND(3, "Not Found"),

    ;

    override val header: String = "COMMON"
}
