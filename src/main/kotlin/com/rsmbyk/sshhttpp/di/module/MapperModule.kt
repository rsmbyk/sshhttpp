package com.rsmbyk.sshhttpp.di.module

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.rsmbyk.sshhttpp.db.entity.Mapper
import com.rsmbyk.sshhttpp.db.entity.TaskEntity
import com.rsmbyk.sshhttpp.mapper.TaskMapper
import com.rsmbyk.sshhttpp.mapper.TaskSpoolerMapper
import com.rsmbyk.sshhttpp.model.Task
import com.rsmbyk.sshhttpp.ts.TaskSpoolerInfoMapper
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
    fun provideTaskMapper (): Mapper<TaskEntity, Task> =
        TaskMapper ()

    @Provides
    fun provideTaskSpoolerMapper (): TaskSpoolerInfoMapper<Task> =
        TaskSpoolerMapper ()
}