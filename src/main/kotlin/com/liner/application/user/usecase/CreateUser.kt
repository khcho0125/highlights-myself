package com.liner.application.user.usecase

import com.liner.domain.user.User
import com.liner.persistence.Transaction.dbQuery
import com.liner.persistence.user.repository.UserRepository
import kotlinx.serialization.Serializable

class CreateUser(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(): Response = dbQuery {
        val userId: Int = userRepository.insert(User())
        return@dbQuery Response(userId)
    }

    @Serializable
    data class Response(
        val userId: Int,
    )
}
