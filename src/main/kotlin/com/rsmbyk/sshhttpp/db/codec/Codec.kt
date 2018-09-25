package com.rsmbyk.sshhttpp.db.codec

import org.bson.Document

interface Codec<Model> {

    fun toDocument (model: Model): Document
    fun toModel (document: Document): Model
}