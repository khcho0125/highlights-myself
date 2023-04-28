package com.liner.persistence.collection.factory

import com.liner.domain.collection.Collection
import com.liner.persistence.collection.entity.CollectionTable
import com.liner.persistence.collection.repository.CollectionRepository
import org.jetbrains.exposed.sql.insertAndGetId
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

    override suspend fun existsByUserIdAndNameAndParentId(userId: Int, name: String, parentId: Int?): Boolean {
        return CollectionTable
            .select {
                CollectionTable.userId eq userId
                CollectionTable.name eq name
                CollectionTable.parentId eq parentId
            }
            .empty()
    }

    override suspend fun insert(collection: Collection): Int {
        return CollectionTable.insertAndGetId {
            it[userId] = collection.userId
            it[name] = collection.name
            it[parentId] = collection.parentId
        }.value
    }
}