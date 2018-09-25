package com.rsmbyk.sshhttpp.db.entity

interface Mapper<E: Entity, M> {

    fun toEntity (model: M): E
    fun toModel (entity: E): M
}