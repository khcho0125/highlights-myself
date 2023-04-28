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

        // 유저 ID 검증
        if(userRepository.existsById(userId)) {
            throw UserException.NotFound()
        }

        // 같은 계층의 이름 중복 검증
        if(collectionRepository.existsByUserIdAndNameAndParentId(
            userId = userId,
            name = request.collectionName,
            parentId = request.parentCollectionId
        )) {
            throw CollectionException.DuplicatedName()
        }

        // 유저의 부모 컬렉션 여부 검증
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
        val parentCollectionId: Int? = null
    )

    @Serializable
    data class Response(
        val collectionId: Int
    )

}