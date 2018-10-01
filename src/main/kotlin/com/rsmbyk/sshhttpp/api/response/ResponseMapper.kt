package com.rsmbyk.sshhttpp.api.response

interface ResponseMapper<R: Response, Model> {

    fun toResponse (model: Model): R
}
