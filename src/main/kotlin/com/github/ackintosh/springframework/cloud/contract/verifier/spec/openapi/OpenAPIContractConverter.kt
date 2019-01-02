package com.github.ackintosh.springframework.cloud.contract.verifier.spec.openapi

import groovy.lang.Closure
import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.parser.OpenAPIV3Parser
import io.swagger.v3.parser.core.models.ParseOptions
import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.ContractConverter
import org.springframework.cloud.contract.spec.internal.Request
import org.springframework.cloud.contract.spec.internal.Response
import java.io.File
import java.lang.RuntimeException

class OpenAPIContractConverter : ContractConverter<Collection<PathItem>> {
    override fun isAccepted(file: File?): Boolean {
        if (file == null) {
            return false
        }

        val parseResult = OpenAPIV3Parser().readLocation(file.path, null, ParseOptions())

        if (parseResult.openAPI == null) {
            return false
        }

        val errorMessages = parseResult.messages
        if (errorMessages.size > 0) {
            throw RuntimeException("The spec ${file.path} has error(s): $errorMessages")
        }

        return parseResult.openAPI.paths.size > 0
    }

    override fun convertFrom(file: File): MutableCollection<Contract> {
        val parseResult = OpenAPIV3Parser().readLocation(file.path, null, ParseOptions())

        val list = arrayListOf<Contract>()
        for ((path, pathItem) in parseResult.openAPI.paths) {
            list.addAll(
                    pathItem.readOperationsMap()
                            .filter {
                                it.value.responses != null && it.value.responses.size > 0
                            }
                            .map {
                                val c = Contract.make(object: Closure<Unit>(null) {
                                    fun doCall() {}
                                })
                                c.name(it.value.operationId)
                                c.description(it.value.description)

                                val req = Request()
                                req.method(it.key.toString())
                                req.urlPath(path)
                                c.request = req

                                val res = Response()
                                val apiResponses = it.value.responses
                                // NOTE: Picks the first one only
                                val (status, apiResponse) = apiResponses.toList().first()
                                res.status(status.toInt())
                                c.response = res
                                c
                            }
            )
        }

        return list
    }

    override fun convertTo(contract: MutableCollection<Contract>?): Collection<PathItem> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
