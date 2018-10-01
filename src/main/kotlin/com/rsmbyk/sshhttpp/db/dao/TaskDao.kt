package com.rsmbyk.sshhttpp.db.dao

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.rsmbyk.sshhttpp.db.entity.Codec
import com.rsmbyk.sshhttpp.db.entity.TaskEntity
import org.bson.Document

class TaskDao (private val col: MongoCollection<Document>, private val codec: Codec<TaskEntity>) {

    fun all (): List<TaskEntity> =
        col.find ().toList ().map (codec::toEntity)

    fun get (id: String): TaskEntity? =
        all ().firstOrNull { it._id.toHexString () == id }

    fun insert (task: TaskEntity) =
        col.insertOne (codec.toDocument (task))

    fun update (task: TaskEntity) =
        col.updateOne (
            Filters.eq ("_id", task._id),
            Document ("\$set", codec.toDocument (task)))

    fun drop () =
        col.drop ()
}
