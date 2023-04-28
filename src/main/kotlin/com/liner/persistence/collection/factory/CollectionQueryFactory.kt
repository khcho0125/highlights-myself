package com.liner.persistence.collection.factory

import com.liner.persistence.collection.entity.CollectionTable
import com.liner.persistence.collection.repository.CollectionRepository
import org.jetbrains.exposed.sql.select

class CollectionQueryFactory : CollectionRepository {

    override suspend fun existsByIdAndUserId(id: Int, userId: Int): Boolean {
        return CollectionTable
            .select {
                CollectionTable.id eq id
                CollectionTable.userId eq userId
            }
            .empty()
    }
}