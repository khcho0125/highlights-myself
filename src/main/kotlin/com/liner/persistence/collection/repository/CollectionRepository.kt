package com.liner.persistence.collection.repository

interface CollectionRepository {

    suspend fun existsById(id: Int) : Boolean
}
