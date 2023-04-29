package com.liner.persistence.collection.repository

import com.liner.domain.collection.Collection

interface CollectionRepository {

    suspend fun findAllByParentIdWithPagination(parentId: Int? ,size: Int): List<Collection>

    suspend fun findAllByParentIds(parentIds: List<Int>): List<Collection>

    suspend fun existsByIdAndUserId(id: Int, userId: Int): Boolean

    suspend fun existsByUserIdAndNameAndParentId(userId: Int, name: String, parentId: Int?): Boolean

    suspend fun insert(collection: Collection): Int
}
