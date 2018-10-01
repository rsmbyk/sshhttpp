package com.rsmbyk.sshhttpp.api.request

interface RequestMapper<R: Request, Model> {

    fun toModel (request: R): Model
}
