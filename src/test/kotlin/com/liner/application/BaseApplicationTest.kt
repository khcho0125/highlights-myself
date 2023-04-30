package com.liner.application

import com.liner.persistence.Transaction
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.mockk.coEvery
import io.mockk.mockkObject

abstract class BaseApplicationTest(testcase: DescribeSpec.() -> Unit) : DescribeSpec(testcase) {

    override suspend fun beforeEach(testCase: TestCase) {
        mockkObject(Transaction)
        coEvery { Transaction.dbQuery(any<suspend () -> Any>()) } coAnswers {
            firstArg<suspend () -> Any>().invoke()
        }
    }
}
