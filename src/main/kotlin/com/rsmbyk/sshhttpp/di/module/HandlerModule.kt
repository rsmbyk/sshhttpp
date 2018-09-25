package com.rsmbyk.sshhttpp.di.module

import com.fasterxml.jackson.databind.ObjectMapper
import com.rsmbyk.sshhttpp.controller.TaskController
import com.rsmbyk.sshhttpp.db.dao.TaskDao
import com.rsmbyk.sshhttpp.db.entity.Mapper
import com.rsmbyk.sshhttpp.db.entity.TaskEntity
import com.rsmbyk.sshhttpp.handler.Handler
import com.rsmbyk.sshhttpp.model.Prop
import com.rsmbyk.sshhttpp.model.Task
import com.rsmbyk.sshhttpp.ts.TaskSpoolerInfoMapper
import dagger.Module
import dagger.Provides

@Module
class HandlerModule {

    @Provides
    fun provideTaskController (
        dao: TaskDao,
        objectMapper: ObjectMapper,
        taskMapper: Mapper<TaskEntity, Task>,
        taskSpoolerInfoMapper: TaskSpoolerInfoMapper<Task>)
            : TaskController =
        TaskController (dao, objectMapper, taskMapper, taskSpoolerInfoMapper)

    @Provides
    fun provideHandler (prop: Prop): Handler =
        Handler (prop)
}