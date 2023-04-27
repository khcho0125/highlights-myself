package com.liner.application.user.usecase

import com.liner.config.exception.UserException
import com.liner.domain.user.User
import com.liner.persistence.dbQuery
import com.liner.persistence.user.repository.UserRepository

class CreateUser(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(request: Request): Unit = dbQuery {
        if (userRepository.existsById(request.userId)) {
            throw UserException.AlreadyCreated()
        }

        userRepository.insert(
            User(id = request.userId)
        )
    }

    data class Request(
        val userId: Int
    )
}
