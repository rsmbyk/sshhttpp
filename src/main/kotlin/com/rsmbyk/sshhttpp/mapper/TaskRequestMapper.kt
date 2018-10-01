package com.rsmbyk.sshhttpp.mapper

import com.rsmbyk.sshhttpp.api.request.CommandRequest
import com.rsmbyk.sshhttpp.api.request.RequestMapper
import com.rsmbyk.sshhttpp.api.request.TaskRequest
import com.rsmbyk.sshhttpp.model.Command
import com.rsmbyk.sshhttpp.model.Task

class TaskRequestMapper (private val commandMapper: RequestMapper<CommandRequest, Command>): RequestMapper<TaskRequest, Task> {

    override fun toModel (request: TaskRequest): Task {
        return request.run {
            Task (
                null,
                Task.State.RECEIVED,
                commandMapper.toModel (command),
                null,
                null,
                null,
                null,
                null,
                null
            )
        }
    }
}
