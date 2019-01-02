package com.github.ackintosh.springframework.cloud.contract.verifier.spec.openapi

import java.io.File
import java.lang.RuntimeException
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class OpenAPIContractConverterTest {
    @Test
    fun isAccepted() {
        assertTrue { OpenAPIContractConverter().isAccepted(File("src/test/resources/is_accepted.yaml")) }
    }

    @Test
    fun isAcceptedReturnsFalseWhenTheParameterIsNull() {
        assertFalse { OpenAPIContractConverter().isAccepted(null) }
    }

    @Test(expected = RuntimeException::class)
    fun isAcceptedThrowsException() {
        OpenAPIContractConverter().isAccepted(File("src/test/resources/is_accepted_invalid.yaml"))
    }
}
