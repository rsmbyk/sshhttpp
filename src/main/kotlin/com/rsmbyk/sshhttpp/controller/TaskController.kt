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
import java.util.*

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

        val response =
            if (context.queryParam ("mock") != null)
                requests
                    .asSequence ()
                    .map { Random ().nextInt () }
                    .map (::mockTaskInfo)
                    .mapIndexed { index, task -> task.copy (request = objectMapper.writeValueAsString (requests[index])) }
                    .map (taskMapper::toEntity)
                    .onEach (dao::insert)
                    .map (taskMapper::toModel)
                    .toList ()
            else
                requests
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

    private fun mockTaskInfo (id: Int): Task {
        val mockCalendar = Calendar.getInstance ()
        mockCalendar.timeInMillis = Random ().nextLong ()
        val mockDate = mockCalendar.time

        return Task (
            id,
            null,
            "command",
            Task.State.FINISHED,
            mockDate,
            mockDate,
            mockDate,
            0.61197,
            0,
            "output"
        )
    }

    fun get (context: Context) {
        val id = context.pathParam ("id").toInt ()
        val task = dao.get (id)
        if (task == null) throw NotFoundResponse ("Task with given 'id' not found")
        else context.json (task)
    }
}