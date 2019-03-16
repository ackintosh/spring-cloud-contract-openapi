package com.github.ackintosh.springframework.cloud.contract.verifier.spec.openapi

import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.parser.OpenAPIV3Parser
import io.swagger.v3.parser.core.models.ParseOptions
import io.swagger.v3.parser.core.models.SwaggerParseResult
import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.ContractConverter

class OpenAPIContractConverter implements ContractConverter<Collection<PathItem>> {
    @Override
    boolean isAccepted(File file) {
        if (file == null) {
            return false;
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
            it.value.readOperationsMap().findAll {
                it.value.responses != null && it.value.responses.size() > 0
            }.each {
                // TODO
                Contract.make {

                }
            }
        }).flatten()
    }

    @Override
    Collection<PathItem> convertTo(Collection collection) {
        throw new IllegalStateException("`convertTo` isn't supported.")
    }
}
