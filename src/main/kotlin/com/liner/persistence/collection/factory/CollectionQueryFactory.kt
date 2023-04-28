package com.liner.persistence.collection.factory

import com.liner.domain.collection.Collection
import com.liner.persistence.collection.entity.CollectionTable
import com.liner.persistence.collection.repository.CollectionRepository
import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import javax.management.Query.and

class CollectionQueryFactory : CollectionRepository {

    override suspend fun existsByIdAndUserId(id: Int, userId: Int): Boolean {
        return CollectionTable
            .select {
                CollectionTable.id eq id
                CollectionTable.userId eq userId
            }
            .limit(1)
            .empty().not()
    }

    override suspend fun existsByUserIdAndNameAndParentId(userId: Int, name: String, parentId: Int?): Boolean {
        return CollectionTable
            .select {
                CollectionTable.userId eq userId and
                (CollectionTable.name like name) and
                (CollectionTable.parentId eq parentId)
            }
            .limit(1)
            .empty().not()
    }

    override suspend fun insert(collection: Collection): Int {
        return CollectionTable.insertAndGetId {
            it[userId] = collection.userId
            it[name] = collection.name
            it[parentId] = collection.parentId
        }.value
    }
}