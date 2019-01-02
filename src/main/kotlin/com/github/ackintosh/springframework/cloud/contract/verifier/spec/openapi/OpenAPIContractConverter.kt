package com.github.ackintosh.springframework.cloud.contract.verifier.spec.openapi

import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.parser.OpenAPIV3Parser
import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.ContractConverter
import java.io.File

class OpenAPIContractConverter : ContractConverter<Collection<PathItem>> {
    override fun isAccepted(file: File?): Boolean {
        if (file == null) {
            return false
        }

        val spec = OpenAPIV3Parser().read(file.path)

        if (spec == null) {
            return false
        }

        return spec.paths.size > 0
    }

    override fun convertFrom(file: File?): MutableCollection<Contract> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun convertTo(contract: MutableCollection<Contract>?): Collection<PathItem> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
