package com.rsmbyk.sshhttpp.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId
import java.util.*

data class Task (
    val id: String?,
    val state: State,
    val command: Command,
    val enqueueTime: Date?,
    val startTime: Date?,
    val endTime: Date?,
    val executionTime: Double?,
    val errorLevel: Int?,
    val output: String?) {

    enum class State {
        RECEIVED,
        ENQUEUED,
        RUNNING,
        FINISHED,
        INVALID
    }
}