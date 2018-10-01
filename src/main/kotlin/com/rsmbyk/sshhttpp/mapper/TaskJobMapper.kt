package com.rsmbyk.sshhttpp.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.rsmbyk.sshhttpp.model.Task
import com.rsmbyk.sshhttpp.ts.TSP
import com.rsmbyk.sshhttpp.ts.entity.JobInput
import com.rsmbyk.sshhttpp.ts.entity.JobMapper
import com.rsmbyk.sshhttpp.ts.entity.JobOutput
import com.rsmbyk.sshhttpp.util.toDate

class TaskJobMapper (private val objectMapper: ObjectMapper): JobMapper<JobInput, JobOutput, Task> {

    override fun toJob (model: Task): JobInput {
        return model.run {
            JobInput (
                TSP.LABEL_PREFIX + id,
                command.toString (),
                objectMapper.writeValueAsString (command)
            )
        }
    }

    override fun toModel (job: JobOutput): Task {
        return job.run {
            Task (
                label.removePrefix (TSP.LABEL_PREFIX),
                Task.State.valueOf (state.toUpperCase ()),
                objectMapper.readValue (originalCommand),
                enqueueTime?.toDate (),
                startTime?.toDate (),
                endTime?.toDate (),
                executionTime?.trimEnd ('s')?.toDouble (),
                errorLevel,
                output
            )
        }
    }
}
