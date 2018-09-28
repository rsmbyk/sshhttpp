package com.rsmbyk.sshhttpp.mapper

import com.rsmbyk.sshhttpp.db.entity.Mapper
import com.rsmbyk.sshhttpp.db.entity.TaskEntity
import com.rsmbyk.sshhttpp.model.Task
import com.rsmbyk.sshhttpp.util.toDate

class TaskMapper: Mapper<TaskEntity, Task> {

    override fun toEntity (model: Task): TaskEntity {
        return model.run {
            TaskEntity (
                id,
                request!!,
                command,
                state,
                enqueueTime.time,
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
                id,
                request,
                command,
                state,
                enqueueTime.toDate (),
                startTime?.toDate (),
                endTime?.toDate (),
                executionTime,
                errorLevel,
                output
            )
        }
    }
}