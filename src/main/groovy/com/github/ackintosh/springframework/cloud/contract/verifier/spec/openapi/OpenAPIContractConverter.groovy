package com.github.ackintosh.springframework.cloud.contract.verifier.spec.openapi

import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.oas.models.PathItem.HttpMethod
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.parser.OpenAPIV3Parser
import io.swagger.v3.parser.core.models.ParseOptions
import io.swagger.v3.parser.core.models.SwaggerParseResult
import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.ContractConverter

class OpenAPIContractConverter implements ContractConverter<Collection<PathItem>> {
    @Override
    boolean isAccepted(File file) {
        if (file == null) {
            return false
        }

        SwaggerParseResult parseResult = (new OpenAPIV3Parser()).readLocation(
                file.path, null, new ParseOptions())

        if (parseResult.openAPI == null) {
            return false
        }

        if (parseResult.messages.size() > 0) {
            throw new RuntimeException("The spec ${file.path} has error(s): $parseResult.messages")
        }

        return parseResult.openAPI.paths.size() > 0
    }

    @Override
    Collection<Contract> convertFrom(File file) {
        SwaggerParseResult parseResult = (new OpenAPIV3Parser()).readLocation(file.path, null, new ParseOptions())

        return parseResult.openAPI.getPaths().collect({
            String path = it.key
            it.value.readOperationsMap().findAll {
                it.value.responses != null && it.value.responses.size() > 0 && takeResponse(it.value) != null
            }.collect {
                processOperation(path, it.key, it.value)
            }
        }).flatten()
    }

    @Override
    Collection<PathItem> convertTo(Collection collection) {
        throw new IllegalStateException("`convertTo` isn't supported.")
    }

    private static Map.Entry<String, ApiResponse> takeResponse(Operation operation) {
        return operation.responses.find { it.key == "200" }
    }

    private static Contract processOperation(String path, HttpMethod httpMethod, Operation operation) {
        Map.Entry<String, ApiResponse> responseSpec = takeResponse(operation)
        Map.Entry<String, MediaType> content = responseSpec.value.content.entrySet().iterator().next()

        Contract.make {
            description(operation.description)
            request {
                method httpMethod.toString()
                url path
            }
            response {
                status responseSpec.key.toInteger()
                headers {
                    contentType(content.key)
                }
            }
        }
    }
}
