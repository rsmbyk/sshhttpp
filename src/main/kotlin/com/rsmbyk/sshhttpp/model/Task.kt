package com.rsmbyk.sshhttpp.model

import java.util.*

class Task (
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
