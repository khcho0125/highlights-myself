package com.liner.persistence.collection.repository

interface CollectionRepository {

    suspend fun existsByIdAndUserId(id: Int, userId: Int) : Boolean
}
