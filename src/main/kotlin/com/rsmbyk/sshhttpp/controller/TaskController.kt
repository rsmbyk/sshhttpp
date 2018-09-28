package com.rsmbyk.sshhttpp.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.rsmbyk.sshhttpp.db.dao.TaskDao
import com.rsmbyk.sshhttpp.db.entity.Mapper
import com.rsmbyk.sshhttpp.db.entity.TaskEntity
import com.rsmbyk.sshhttpp.model.Command
import com.rsmbyk.sshhttpp.model.Task
import com.rsmbyk.sshhttpp.ts.TaskSpooler
import com.rsmbyk.sshhttpp.ts.TaskSpoolerInfoMapper
import io.javalin.Context
import io.javalin.NotFoundResponse

class TaskController (
    private val dao: TaskDao,
    private val objectMapper: ObjectMapper,
    private val taskMapper: Mapper<TaskEntity, Task>,
    private val taskSpoolerInfoMapper: TaskSpoolerInfoMapper<Task>) {

    fun all (context: Context) {
        context.json (dao.all ())
    }

    fun create (context: Context) {
        val requests = objectMapper.readValue<List<Command>> (context.body ())

        val response = requests
            .map (TaskSpooler::add)
            .map (TaskSpooler::getInfo)
            .map (taskSpoolerInfoMapper::toModel)
            .mapIndexed { index, task -> task.copy (request = objectMapper.writeValueAsString (requests[index])) }
            .map (taskMapper::toEntity)
            .onEach (dao::insert)
            .map (taskMapper::toModel)
            .toList ()

        context.json (response)
    }

    fun get (context: Context) {
        val id = context.pathParam ("id").toInt ()

        try {
            val task: Task = dao.get (id)!!
                .let (taskMapper::toModel)
                .apply {
                    if (stateEnum == Task.State.RUNNING) {
                        TaskSpooler.getInfo (id)
                            .let(taskSpoolerInfoMapper::toModel)
                            .copy(request = request)
                            .let(taskMapper::toEntity)
                            .also(dao::update)
                            .let(taskMapper::toModel)
                    }
                }
            context.json (task)
        } catch (e: NullPointerException) {
            throw NotFoundResponse ("Task with given 'id' was not found")
        }
    }
}