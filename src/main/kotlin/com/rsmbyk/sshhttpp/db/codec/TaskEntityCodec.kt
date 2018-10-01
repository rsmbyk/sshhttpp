package com.rsmbyk.sshhttpp.db.codec

import com.rsmbyk.sshhttpp.db.entity.Codec
import com.rsmbyk.sshhttpp.db.entity.TaskEntity
import org.bson.Document

class TaskEntityCodec: Codec<TaskEntity> {

    override fun toDocument (entity: TaskEntity): Document {
        return entity.run {
            Document ()
                .append ("_id", _id)
                .append ("state", state)
                .append ("command", command)
                .append ("enqueueTime", enqueueTime)
                .append ("startTime", startTime)
                .append ("endTime", endTime)
                .append ("executionTime", executionTime)
                .append ("errorLevel", errorLevel)
                .append ("output", output)
        }
    }

    override fun toEntity (document: Document): TaskEntity {
        return document.run {
            TaskEntity (
                getObjectId ("_id"),
                getInteger ("state"),
                getString ("command"),
                getLong ("enqueueTime"),
                getLong ("startTime"),
                getLong ("endTime"),
                getDouble ("executionTime"),
                getInteger ("errorLevel"),
                getString ("output")
            )
        }
    }
}
