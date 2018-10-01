package com.rsmbyk.sshhttpp.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.rsmbyk.sshhttpp.db.entity.EntityMapper
import com.rsmbyk.sshhttpp.db.entity.TaskEntity
import com.rsmbyk.sshhttpp.model.Task
import com.rsmbyk.sshhttpp.util.toDate
import org.bson.types.ObjectId

class TaskEntityMapper (private val objectMapper: ObjectMapper): EntityMapper<TaskEntity, Task> {

    override fun toEntity (model: Task): TaskEntity {
        return model.run {
            TaskEntity (
                id?.let (::ObjectId) ?: ObjectId.get (),
                state.ordinal,
                objectMapper.writeValueAsString (command),
                enqueueTime?.time,
                startTime?.time,
                endTime?.time,
                executionTime,
                errorLevel,
                output
            )
        }
    }

    override fun toModel (entity: TaskEntity): Task {
        return entity.run {
            Task (
                _id.toHexString (),
                Task.State.values ()[state],
                objectMapper.readValue (command),
                enqueueTime?.toDate (),
                startTime?.toDate (),
                endTime?.toDate (),
                executionTime,
                errorLevel,
                output
            )
        }
    }
}
