package com.rsmbyk.sshhttpp.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.rsmbyk.sshhttpp.api.request.CommandRequest
import com.rsmbyk.sshhttpp.api.request.RequestMapper
import com.rsmbyk.sshhttpp.api.request.TaskRequest
import com.rsmbyk.sshhttpp.api.response.ResponseMapper
import com.rsmbyk.sshhttpp.api.response.TaskResponse
import com.rsmbyk.sshhttpp.db.dao.TaskDao
import com.rsmbyk.sshhttpp.db.entity.EntityMapper
import com.rsmbyk.sshhttpp.db.entity.TaskEntity
import com.rsmbyk.sshhttpp.model.Task
import com.rsmbyk.sshhttpp.ts.entity.JobMapper
import com.rsmbyk.sshhttpp.ts.TSP
import com.rsmbyk.sshhttpp.ts.entity.JobInput
import com.rsmbyk.sshhttpp.ts.entity.JobOutput
import io.javalin.Context
import io.javalin.NotFoundResponse

class TaskController (
    private val dao: TaskDao,
    private val tsp: TSP,
    private val objectMapper: ObjectMapper,
    private val requestMapper: RequestMapper<TaskRequest, Task>,
    private val responseMapper: ResponseMapper<TaskResponse, Task>,
    private val entityMapper: EntityMapper<TaskEntity, Task>,
    private val jobMapper: JobMapper<JobInput, JobOutput, Task>) {

    fun all (context: Context) {
        dao.all ()
            .asSequence ()
            .map (entityMapper::toModel)
            .map (responseMapper::toResponse)
            .toList ()
            .also { context.json (it) }
    }

    fun create (context: Context) {
        objectMapper.readValue<List<CommandRequest>> (context.body ())
            .asSequence ()
            .map (::TaskRequest)
            .map (requestMapper::toModel)
            .map (entityMapper::toEntity)
            .onEach { dao.insert (it) }
            .map (entityMapper::toModel)
            .map (jobMapper::toJob)
            .map (tsp::add)
            .map (jobMapper::toModel)
            .map (entityMapper::toEntity)
            .onEach { dao.update (it) }
            .map (entityMapper::toModel)
            .map (responseMapper::toResponse)
            .toList ()
            .also { context.json (it) }
    }

    fun get (context: Context) {
        val id = context.pathParam ("id")

        try {
            val task = dao.get (id)!!.let (entityMapper::toModel)

            task.takeIf { it.state.ordinal < 3 }
                ?.let (jobMapper::toJob)
                ?.let (tsp::get)
                ?.let (jobMapper::toModel)
                ?.let (entityMapper::toEntity)
                ?.also { dao.update (it) }
                ?.let (entityMapper::toModel)
                .run { this ?: task }
                .let (responseMapper::toResponse)
                .also { context.json (it) }

        } catch (e: NullPointerException) {
            throw NotFoundResponse ("Task with given 'id' was not found")
        }
    }

    fun drop (context: Context) {
        context.json (dao.drop ())
    }
}
