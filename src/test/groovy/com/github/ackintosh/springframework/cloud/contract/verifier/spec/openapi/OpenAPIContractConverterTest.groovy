package com.github.ackintosh.springframework.cloud.contract.verifier.spec.openapi

import org.springframework.cloud.contract.spec.Contract

import static org.junit.Assert.*
import org.junit.Test

class OpenAPIContractConverterTest {
    @Test
    void isAccepted() {
        assertTrue((new OpenAPIContractConverter()).isAccepted(new File("src/test/resources/is_accepted.yaml")))
    }

    @Test
    void isAcceptedReturnsFalseWhenTheParameterIsNull() {
        assertFalse((new OpenAPIContractConverter().isAccepted(null)))
    }

    @Test(expected = RuntimeException)
    void isAcceptedThrowsException() {
        (new OpenAPIContractConverter()).isAccepted(new File("src/test/resources/is_accepted_invalid.yaml"))
    }

    @Test
    void convertFrom() {
        def expected = [
                Contract.make {
                    description("GET operation for `/convert_from`")
                    request {
                        method(GET())
                        url("/convert_from")
                    }
                    response {
                        status(OK())
                        headers {
                            contentType(applicationJson())
                        }
                    }
                }
        ]
        Collection<Contract> contracts = (new OpenAPIContractConverter()).convertFrom(new File("src/test/resources/convert_from.yaml"))

        assertEquals(expected, contracts)
    }
}
