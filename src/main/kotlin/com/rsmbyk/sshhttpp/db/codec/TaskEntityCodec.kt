package com.rsmbyk.sshhttpp.db.codec

import com.rsmbyk.sshhttpp.db.entity.TaskEntity
import org.bson.Document

class TaskEntityCodec: Codec<TaskEntity> {

    override fun toDocument (model: TaskEntity): Document {
        return model.run {
            Document ()
                .append ("id", id)
                .append ("request", request)
                .append ("command", command)
                .append ("state", state)
                .append ("enqueueTime", enqueueTime)
                .append ("startTime", startTime)
                .append ("endTime", endTime)
                .append ("executionTime", executionTime)
                .append ("errorLevel", errorLevel)
                .append ("output", output)
        }
    }

    override fun toModel (document: Document): TaskEntity {
        return document.run {
            TaskEntity (
                getInteger ("id"),
                getString ("request"),
                getString ("command"),
                getInteger ("state"),
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
