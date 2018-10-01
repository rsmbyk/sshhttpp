package com.rsmbyk.sshhttpp.db.entity

import org.bson.Document

interface Codec<E: Entity> {

    fun toDocument (entity: E): Document
    fun toEntity (document: Document): E
}