package com.rsmbyk.sshhttpp.mapper

import com.rsmbyk.sshhttpp.api.response.ResponseMapper
import com.rsmbyk.sshhttpp.api.response.TaskResponse
import com.rsmbyk.sshhttpp.model.Task

class TaskResponseMapper: ResponseMapper<TaskResponse, Task> {

    override fun toResponse(model: Task): TaskResponse {
        return model.run {
            TaskResponse (
                id!!,
                state.ordinal,
                command.toString (),
                enqueueTime,
                startTime,
                endTime,
                executionTime,
                errorLevel,
                output
            )
        }
    }
}
