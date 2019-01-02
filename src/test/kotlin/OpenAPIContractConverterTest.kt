package com.github.ackintosh.springframework.cloud.contract.verifier.spec.openapi

import org.springframework.cloud.contract.spec.internal.UrlPath
import java.io.File
import java.lang.RuntimeException
import kotlin.test.Test
import kotlin.test.assertEquals
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

    @Test
    fun convertFrom() {
        val contracts = OpenAPIContractConverter().convertFrom(File("src/test/resources/is_accepted.yaml"))
        assertEquals("GET", contracts.first().request.method.clientValue)
        assertEquals(UrlPath("/is_accepted"), contracts.first().request.urlPath)
    }
}
