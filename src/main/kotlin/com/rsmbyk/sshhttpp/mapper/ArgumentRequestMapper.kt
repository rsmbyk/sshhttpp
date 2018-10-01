package com.rsmbyk.sshhttpp.mapper

import com.rsmbyk.sshhttpp.api.request.ArgumentRequest
import com.rsmbyk.sshhttpp.api.request.RequestMapper
import com.rsmbyk.sshhttpp.model.Argument

class ArgumentRequestMapper: RequestMapper<ArgumentRequest, Argument> {

    override fun toModel (request: ArgumentRequest): Argument {
        return request.run {
            Argument (
                hyphen?.toUpperCase ()?.let (Argument.Hyphen::valueOf),
                option,
                parameter
            )
        }
    }
}