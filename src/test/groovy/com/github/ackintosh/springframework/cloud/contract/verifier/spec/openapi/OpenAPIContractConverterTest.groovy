package com.github.ackintosh.springframework.cloud.contract.verifier.spec.openapi

import static org.junit.Assert.*
import org.junit.Test

class OpenAPIContractConverterTest {
    @Test
    void isAccepted() {
        assertTrue((new OpenAPIContractConverter()).isAccepted(new File("src/test/resources/is_accepted.yaml")))
    }
}