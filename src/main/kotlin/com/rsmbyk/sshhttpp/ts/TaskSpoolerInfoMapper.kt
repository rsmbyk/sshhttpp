package com.rsmbyk.sshhttpp.ts

interface TaskSpoolerInfoMapper<M> {

    fun toInfo (model: M): TaskSpoolerInfo
    fun toModel (info: TaskSpoolerInfo): M
}