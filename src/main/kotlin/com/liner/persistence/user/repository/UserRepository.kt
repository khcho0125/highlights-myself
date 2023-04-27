package com.liner.persistence.user.repository

import com.liner.domain.user.User

interface UserRepository {

    suspend fun existsById(id: Int): Boolean

    suspend fun insert(user: User): User
}
