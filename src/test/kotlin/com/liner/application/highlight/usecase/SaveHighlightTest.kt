package com.liner.application.highlight.usecase

import com.liner.application.BaseApplicationTest
import com.liner.config.exception.UserException
import com.liner.persistence.highlight.repository.HighlightRepository
import com.liner.persistence.user.repository.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class SaveHighlightTest : BaseApplicationTest({

    val highlightRepository: HighlightRepository = mockk()
    val userRepository: UserRepository = mockk()

    val saveHighlight = SaveHighlight(
        highlightRepository = highlightRepository,
        userRepository = userRepository,
    )

    describe("하이라이트 저장이 요청되었을 때") {
        val userId = 1
        val request = SaveHighlight.Request(
            content = "내용",
        )

        val highlightId = 1
        val response = SaveHighlight.Response(
            highlightId = highlightId,
        )

        context("해당 ID의 유저가 존재하지 않는다면") {

            coEvery { userRepository.existsById(userId) } returns false

            it("예외를 던진다.") {
                shouldThrow<UserException.NotFound> {
                    saveHighlight(request, userId)
                }
            }
        }

        coEvery { userRepository.existsById(userId) } returns true
        coEvery { highlightRepository.insert(any()) } returns highlightId

        it("Response를 반환한다.") {
            val result: SaveHighlight.Response = saveHighlight(request, userId)

            result shouldBe response
        }
    }
},)
