package com.liner.config.exception

object UserException {

    class NotFound(override val message: String? = null) :
        DomainException.NotFound(UserErrorCode.NOT_FOUND, message)
}

enum class UserErrorCode(
    override val sequence: Int,
    override val defaultMessage: String
) : ErrorCode {

    NOT_FOUND(1, "User Not Found")

    ;

    override val header: String = "USER"
}
