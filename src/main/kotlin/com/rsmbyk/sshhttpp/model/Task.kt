package com.rsmbyk.sshhttpp.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*

data class Task (
    val id: Int,
    val request: String?,
    val command: String,
    val state: Int,
    val enqueueTime: Date,
    val startTime: Date?,
    val endTime: Date?,
    val executionTime: Double?,
    val errorLevel: Int?,
    val output: String?) {

    @JsonIgnore
    val stateEnum = State.values ()[state]

    enum class State {
        FINISHED,
        RUNNING,
        INVALID
    }
}