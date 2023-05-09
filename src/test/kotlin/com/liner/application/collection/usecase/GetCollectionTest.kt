package com.liner.application.collection.usecase

import com.liner.application.BaseApplicationTest
import com.liner.application.collection.collectionFixture
import com.liner.application.highlight.highlightStorageFixtures
import com.liner.config.exception.CollectionException
import com.liner.config.exception.UserException
import com.liner.domain.collection.Collection
import com.liner.domain.highlight.HighlightStorage
import com.liner.persistence.collection.repository.CollectionRepository
import com.liner.persistence.highlight.repository.HighlightStorageRepository
import com.liner.persistence.user.repository.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class GetCollectionTest : BaseApplicationTest({

    val collectionRepository: CollectionRepository = mockk()
    val highlightStorageRepository: HighlightStorageRepository = mockk()
    val userRepository: UserRepository = mockk()

    val getCollection = GetCollection(
        collectionRepository = collectionRepository,
        highlightStorageRepository = highlightStorageRepository,
        userRepository = userRepository,
    )

    describe("컬렉션 목록을 가져올 때") {
        val userId = 1
        val cursorCollectionId = 1
        val size = 1

        val collection: Collection = collectionFixture.copy(
            id = 2,
            userId = userId,
            parentId = cursorCollectionId,
        )
        val collections: List<Collection> = listOf(collection)
        val collectionIds: List<Int> = collections.map(Collection::id)
        val highlightStorages: List<HighlightStorage> = listOf(
            highlightStorageFixtures.copy(
                collectionId = collection.id,
            ),
        )

        val response = GetCollection.Response(
            collections = listOf(
                GetCollection.CollectionView(
                    collectionId = collection.id,
                    highlightCount = highlightStorages.size,
                    name = collection.name,
                    children = emptyList(),
                ),
            ),
        )

        context("해당 ID의 유저가 존재하지 않으면") {
            coEvery { userRepository.existsById(userId) } returns false

            it("예외를 던진다.") {
                shouldThrow<UserException.NotFound> {
                    getCollection(userId, cursorCollectionId, size)
                }
            }
        }

        context("탐색하는 컬렉션이 존재하지 않으면") {
            coEvery { userRepository.existsById(userId) } returns true
            coEvery { collectionRepository.existsByIdAndUserId(cursorCollectionId, userId) } returns false

            it("예외를 던진다.") {
                shouldThrow<CollectionException.NotFound> {
                    getCollection(userId, cursorCollectionId, size)
                }
            }
        }

        coEvery { userRepository.existsById(userId) } returns true
        coEvery { collectionRepository.existsByIdAndUserId(cursorCollectionId, userId) } returns true
        coEvery {
            collectionRepository.findAllByParentIdWithPagination(
                parentId = cursorCollectionId,
                size = size,
            )
        } returns collections
        coEvery { collectionRepository.findAllByParentIds(collectionIds) } returns emptyList()
        coEvery { highlightStorageRepository.findAllByCollectionIds(collectionIds) } returns highlightStorages

        it("Response를 반환한다.") {
            val result: GetCollection.Response = getCollection(userId, cursorCollectionId, size)

            result shouldBe response
        }
    }
},)
