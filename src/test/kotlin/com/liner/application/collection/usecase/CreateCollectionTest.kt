package com.liner.application.collection.usecase

import com.liner.application.BaseApplicationTest
import com.liner.config.exception.CollectionException
import com.liner.config.exception.UserException
import com.liner.persistence.collection.repository.CollectionRepository
import com.liner.persistence.user.repository.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class CreateCollectionTest : BaseApplicationTest({

    val userRepository: UserRepository = mockk()
    val collectionRepository: CollectionRepository = mockk()

    val createCollection = CreateCollection(
        userRepository = userRepository,
        collectionRepository = collectionRepository,
    )

    describe("컬렉션을 생성할 때") {
        val userId = 1
        val request: CreateCollection.Request = CreateCollection.Request(
            collectionName = "컬렉션",
            parentCollectionId = 10,
        )

        val collectionId = 1
        val response = CreateCollection.Response(
            collectionId = collectionId,
        )

        context("해당 ID의 유저가 존재하지 않으면") {
            coEvery { userRepository.existsById(userId) } returns false

            it("예외를 던진다.") {
                shouldThrow<UserException.NotFound> {
                    createCollection(request, userId)
                }
            }
        }

        context("같은 계층의 컬렉션 중 같은 컬렉션 이름이 존재하면") {
            coEvery { userRepository.existsById(userId) } returns true
            coEvery {
                collectionRepository.existsByUserIdAndNameAndParentId(
                    userId = userId,
                    name = request.collectionName,
                    parentId = request.parentCollectionId,
                )
            } returns true

            it("예외를 던진다.") {
                shouldThrow<CollectionException.DuplicatedName> {
                    createCollection(request, userId)
                }
            }
        }

        context("유저 컬렉션 중 부모 컬렉션이 존재하지 않는다면") {
            coEvery { userRepository.existsById(userId) } returns true
            coEvery {
                collectionRepository.existsByUserIdAndNameAndParentId(
                    userId = userId,
                    name = request.collectionName,
                    parentId = request.parentCollectionId,
                )
            } returns false
            coEvery { collectionRepository.existsByIdAndUserId(request.parentCollectionId!!, userId) } returns false

            it("예외를 던진다.") {
                shouldThrow<CollectionException.NotFound> {
                    createCollection(request, userId)
                }
            }
        }

        coEvery { userRepository.existsById(userId) } returns true
        coEvery {
            collectionRepository.existsByUserIdAndNameAndParentId(
                userId = userId,
                name = request.collectionName,
                parentId = request.parentCollectionId,
            )
        } returns false
        coEvery { collectionRepository.existsByIdAndUserId(request.parentCollectionId!!, userId) } returns true
        coEvery { collectionRepository.insert(any()) } returns collectionId

        it("Response를 반환한다.") {
            val result: CreateCollection.Response = createCollection(request, userId)

            result shouldBe response
        }
    }
},)
