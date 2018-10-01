package com.rsmbyk.sshhttpp.ts.entity

interface JobMapper<I: Job, O: Job, M> {

    fun toJob (model: M): I
    fun toModel (job: O): M
}
