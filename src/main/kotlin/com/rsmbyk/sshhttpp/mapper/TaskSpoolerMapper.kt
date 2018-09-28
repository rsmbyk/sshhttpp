package com.rsmbyk.sshhttpp.mapper

import com.rsmbyk.sshhttpp.model.Task
import com.rsmbyk.sshhttpp.ts.TaskSpoolerInfo
import com.rsmbyk.sshhttpp.ts.TaskSpoolerInfoMapper
import com.rsmbyk.sshhttpp.util.toDate

class TaskSpoolerMapper: TaskSpoolerInfoMapper<Task> {

    override fun toInfo (model: Task): TaskSpoolerInfo =
        throw NotImplementedError ()

    override fun toModel (info: TaskSpoolerInfo): Task {
        return info.run {
            Task (
                id,
                null,
                command,
                Task.State.valueOf (state.toUpperCase ()).ordinal,
                enqueueTime.toDate (),
                startTime?.toDate (),
                endTime?.toDate (),
                executionTime?.trimEnd ('s')?.toDouble (),
                errorLevel,
                output
            )
        }
    }
}