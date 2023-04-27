package com.liner.config.exception

sealed class DomainException(
    open val code: ErrorCode,
    override val message: String?
) : Throwable(message) {

    open class BadRequest(
        override val code: ErrorCode = DomainErrorCode.BAD_REQUEST,
        override val message: String? = null
    ) : DomainException(code, message)

    open class Unauthorized(
        override val code: ErrorCode = DomainErrorCode.UNAUTHORIZED,
        override val message: String? = null
    ) : DomainException(code, message)

    open class NotFound(
        override val code: ErrorCode = DomainErrorCode.NOT_FOUND,
        override val message: String? = null
    ) : DomainException(code, message)

    open class InternalError(
        override val code: ErrorCode = DomainErrorCode.INTERNAL_SERVER_ERROR,
        override val message: String? = null
    ) : DomainException(code, message)
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
    override val defaultMessage: String
) : ErrorCode {

    BAD_REQUEST(1, "Bad Request"),
    UNAUTHORIZED(2, "Unauthorized"),
    NOT_FOUND(3, "Not Found"),
    INTERNAL_SERVER_ERROR(4, "Internal Server Error")

    ;

    override val header: String = "COMMON"
}
