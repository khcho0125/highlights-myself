package com.liner.persistence.collection.factory

import com.liner.domain.collection.Collection
import com.liner.persistence.collection.entity.CollectionTable
import com.liner.persistence.collection.repository.CollectionRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select

class CollectionQueryFactory : CollectionRepository {

    private fun toDomain(row: ResultRow): Collection = Collection(
        id = row[CollectionTable.id].value,
        name = row[CollectionTable.name],
        parentId = row[CollectionTable.parentId]?.value,
        userId = row[CollectionTable.userId].value,
    )

    override suspend fun findAllByParentIdWithPagination(parentId: Int?, size: Int): List<Collection> {
        return CollectionTable
            .select { CollectionTable.parentId eq parentId }
            .limit(size)
            .map(::toDomain)
    }

    override suspend fun findAllByParentIds(parentIds: List<Int>): List<Collection> {
        return CollectionTable
            .select { CollectionTable.parentId inList parentIds }
            .map(::toDomain)
    }

    override suspend fun existsByIdAndUserId(id: Int, userId: Int): Boolean {
        return CollectionTable
            .select {
                CollectionTable.id eq id and
                    (CollectionTable.userId eq userId)
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
