package com.rsmbyk.sshhttpp.di.module

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.rsmbyk.sshhttpp.api.request.ArgumentRequest
import com.rsmbyk.sshhttpp.api.request.CommandRequest
import com.rsmbyk.sshhttpp.api.request.RequestMapper
import com.rsmbyk.sshhttpp.api.request.TaskRequest
import com.rsmbyk.sshhttpp.api.response.ResponseMapper
import com.rsmbyk.sshhttpp.api.response.TaskResponse
import com.rsmbyk.sshhttpp.db.entity.EntityMapper
import com.rsmbyk.sshhttpp.db.entity.TaskEntity
import com.rsmbyk.sshhttpp.mapper.*
import com.rsmbyk.sshhttpp.model.Argument
import com.rsmbyk.sshhttpp.model.Command
import com.rsmbyk.sshhttpp.model.Task
import com.rsmbyk.sshhttpp.ts.entity.JobInput
import com.rsmbyk.sshhttpp.ts.entity.JobMapper
import com.rsmbyk.sshhttpp.ts.entity.JobOutput
import dagger.Module
import dagger.Provides

@Module
class MapperModule {

    @Provides
    fun provideObjectMapper (): ObjectMapper {
        return jacksonObjectMapper().apply {
            configure (DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
        }
    }

    @Provides
    fun provideArgumentRequestMapper (): RequestMapper<ArgumentRequest, Argument> =
        ArgumentRequestMapper ()

    @Provides
    fun provideCommandRequestMapper (argumentMapper: RequestMapper<ArgumentRequest, Argument>)
            : RequestMapper<CommandRequest, Command> =
        CommandRequestMapper (argumentMapper)

    @Provides
    fun provideTaskRequestMapper (commandMapper: RequestMapper<CommandRequest, Command>)
            : RequestMapper<TaskRequest, Task> =
        TaskRequestMapper (commandMapper)

    @Provides
    fun provideTaskEntityMapper (objectMapper: ObjectMapper): EntityMapper<TaskEntity, Task> =
            TaskEntityMapper (objectMapper)

    @Provides
    fun provideJobInputMapper (objectMapper: ObjectMapper): JobMapper<JobInput, JobOutput, Task> =
        TaskJobMapper (objectMapper)

    @Provides
    fun provideTaskResponseMapper (): ResponseMapper<TaskResponse, Task> =
        TaskResponseMapper ()
}