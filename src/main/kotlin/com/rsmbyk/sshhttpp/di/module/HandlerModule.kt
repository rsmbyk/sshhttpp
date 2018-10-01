package com.rsmbyk.sshhttpp.di.module

import com.fasterxml.jackson.databind.ObjectMapper
import com.rsmbyk.sshhttpp.api.request.RequestMapper
import com.rsmbyk.sshhttpp.api.request.TaskRequest
import com.rsmbyk.sshhttpp.api.response.ResponseMapper
import com.rsmbyk.sshhttpp.api.response.TaskResponse
import com.rsmbyk.sshhttpp.controller.TaskController
import com.rsmbyk.sshhttpp.db.dao.TaskDao
import com.rsmbyk.sshhttpp.db.entity.EntityMapper
import com.rsmbyk.sshhttpp.db.entity.TaskEntity
import com.rsmbyk.sshhttpp.handler.Handler
import com.rsmbyk.sshhttpp.model.Prop
import com.rsmbyk.sshhttpp.model.Task
import com.rsmbyk.sshhttpp.ts.TSP
import com.rsmbyk.sshhttpp.ts.entity.JobInput
import com.rsmbyk.sshhttpp.ts.entity.JobMapper
import com.rsmbyk.sshhttpp.ts.entity.JobOutput
import dagger.Module
import dagger.Provides

@Module
class HandlerModule {

    @Provides
    fun provideTaskController (
        dao: TaskDao,
        tsp: TSP,
        objectMapper: ObjectMapper,
        requestMapper: RequestMapper<TaskRequest, Task>,
        responseMapper: ResponseMapper<TaskResponse, Task>,
        entityMapper: EntityMapper<TaskEntity, Task>,
        inputMapper: JobMapper<JobInput, JobOutput, Task>)
            : TaskController =
        TaskController (dao, tsp, objectMapper, requestMapper, responseMapper, entityMapper, inputMapper)

    @Provides
    fun provideHandler (prop: Prop): Handler =
        Handler (prop)
}