package com.liner.persistence.user.factory

import com.liner.domain.user.User
import com.liner.persistence.user.entity.UserTable
import com.liner.persistence.user.repository.UserRepository
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select

class UserQueryFactory : UserRepository {

    override suspend fun insert(user: User): Int {
        return UserTable.insertAndGetId {}.value
    }

    override suspend fun existsById(id: Int): Boolean {
        return UserTable
            .select { UserTable.id eq id }
            .limit(1)
            .empty().not()
    }
}
