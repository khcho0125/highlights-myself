package com.liner.application.highlight.usecase

import com.liner.application.BaseApplicationTest
import com.liner.config.exception.CollectionException
import com.liner.config.exception.HighlightException
import com.liner.config.exception.UserException
import com.liner.persistence.collection.repository.CollectionRepository
import com.liner.persistence.highlight.repository.HighlightRepository
import com.liner.persistence.highlight.repository.HighlightStorageRepository
import com.liner.persistence.user.repository.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.coEvery
import io.mockk.mockk

class StoreInCollectionTest : BaseApplicationTest({

    val collectionRepository: CollectionRepository = mockk()
    val highlightStorageRepository: HighlightStorageRepository = mockk()
    val highlightRepository: HighlightRepository = mockk()
    val userRepository: UserRepository = mockk()

    val storeInCollection = StoreInCollection(
        collectionRepository = collectionRepository,
        highlightStorageRepository = highlightStorageRepository,
        highlightRepository = highlightRepository,
        userRepository = userRepository,
    )

    val highlightId = 1
    val collectionId = 1
    val request = StoreInCollection.Request(
        userId = 1,
        collectionIds = listOf(collectionId),
    )

    describe("하이라이트를 컬렉션에 저장할 때") {

        context("해당 ID의 유저가 존재하지 않다면") {
            coEvery { userRepository.existsById(request.userId) } returns false

            it("예외를 던진다.") {
                shouldThrow<UserException.NotFound> {
                    storeInCollection(request, highlightId)
                }
            }
        }

        context("해당 ID의 하이라이트가 존재하지 않다면") {
            coEvery { userRepository.existsById(request.userId) } returns true
            coEvery { highlightRepository.existsById(highlightId) } returns false

            it("예외를 던진다.") {
                shouldThrow<HighlightException.NotFound> {
                    storeInCollection(request, highlightId)
                }
            }
        }

        context("해당 ID의 컬렉션이 존재하지 않다면") {
            coEvery { userRepository.existsById(request.userId) } returns true
            coEvery { highlightRepository.existsById(highlightId) } returns true
            coEvery { collectionRepository.existsByIdAndUserId(collectionId, request.userId) } returns false

            it("예외를 던진다.") {
                shouldThrow<CollectionException.NotFound> {
                    storeInCollection(request, highlightId)
                }
            }
        }
    }
},)
