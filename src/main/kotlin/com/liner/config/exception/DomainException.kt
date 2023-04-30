package com.liner.config.exception

sealed class DomainException(
    override val message: String?,
    open val code: ErrorCode,
) : Throwable(message) {

    open class BadRequest(
        override val message: String? = null,
        override val code: ErrorCode = DomainErrorCode.BAD_REQUEST,
    ) : DomainException(message, code)

    open class NotFound(
        override val message: String? = null,
        override val code: ErrorCode = DomainErrorCode.NOT_FOUND,
    ) : DomainException(message, code)

    open class Conflict(
        override val message: String? = null,
        override val code: ErrorCode = DomainErrorCode.CONFLICT,
    ) : DomainException(message, code)

    open class InternalError(
        override val message: String? = null,
        override val code: ErrorCode = DomainErrorCode.INTERNAL_SERVER_ERROR,
    ) : DomainException(message, code)
}

interface ErrorCode {
    val sequence: Int
    val defaultMessage: String

    val header: String

    fun serial(): String = "$header-${formatPattern.format(sequence)}"

    companion object {
        const val formatPattern: String = "%03d"
    }
}

enum class DomainErrorCode(
    override val sequence: Int,
    override val defaultMessage: String,
) : ErrorCode {

    BAD_REQUEST(1, "Bad Request"),
    NOT_FOUND(2, "Not Found"),
    CONFLICT(3, "Conflict"),
    INTERNAL_SERVER_ERROR(4, "Internal Server Error"),

    ;

    override val header: String = "COMMON"
}
