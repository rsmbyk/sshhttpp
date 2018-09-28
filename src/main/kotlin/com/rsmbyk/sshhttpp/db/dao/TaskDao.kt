package com.rsmbyk.sshhttpp.db.dao

import com.fasterxml.jackson.databind.ObjectMapper
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.IndexOptions
import com.rsmbyk.sshhttpp.db.codec.Codec
import com.rsmbyk.sshhttpp.db.entity.TaskEntity
import org.bson.Document

class TaskDao (private val col: MongoCollection<Document>, private val codec: Codec<TaskEntity>, private val mapper: ObjectMapper) {

    init {
        col.createIndex (
            Document ("id", 1),
            IndexOptions ().unique (true))
    }

    fun all (): List<TaskEntity> =
        col.find ().toList ().map (codec::toModel)

    fun get (id: Int): TaskEntity? =
            all ().firstOrNull { it.id == id }

    fun insert (task: TaskEntity) =
        col.insertOne (codec.toDocument (task))

    fun update (task: TaskEntity) {
        col.updateOne(
            Filters.eq ("id", task.id),
            Document ("\$set", Document.parse (mapper.writeValueAsString (task))))
    }

    fun drop () =
        col.drop ()
}