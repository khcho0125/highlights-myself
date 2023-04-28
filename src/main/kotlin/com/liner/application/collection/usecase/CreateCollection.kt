package com.liner.application.collection.usecase

import com.liner.config.exception.CollectionException
import com.liner.config.exception.UserException
import com.liner.domain.collection.Collection
import com.liner.persistence.collection.repository.CollectionRepository
import com.liner.persistence.dbQuery
import com.liner.persistence.user.repository.UserRepository
import kotlinx.serialization.Serializable

class CreateCollection(
    private val userRepository: UserRepository,
    private val collectionRepository: CollectionRepository
) {

    suspend operator fun invoke(request: Request, userId: Int) : Response = dbQuery {
        if(userRepository.existsById(userId)) {
            throw UserException.NotFound()
        }

        request.parentCollectionId?.let {
            if(collectionRepository.existsByIdAndUserId(it, userId)) {
                throw CollectionException.NotFound("Parent Collection Not Found")
            }
        }

        return@dbQuery collectionRepository.insert(
            Collection(
                userId = userId,
                name = request.collectionName,
                parentId = request.parentCollectionId
            )
        ).let(::Response)
    }

    @Serializable
    data class Request(
        val collectionName: String,
        val parentCollectionId: Int?
    )

    @Serializable
    data class Response(
        val collectionId: Int
    )

}