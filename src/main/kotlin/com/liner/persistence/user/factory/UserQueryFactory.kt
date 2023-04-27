package com.liner.persistence.user.factory

import com.liner.domain.user.User
import com.liner.persistence.user.entity.UserTable
import com.liner.persistence.user.repository.UserRepository
import org.jetbrains.exposed.sql.insertAndGetId

class UserQueryFactory : UserRepository {

    override suspend fun insert(user: User): Int {
        return UserTable.insertAndGetId { }.value
    }
}