package com.rsmbyk.sshhttpp.model

import java.util.*

data class Task (
    val id: Int,
    val request: String?,
    val command: String,
    val state: State,
    val enqueueTime: Date,
    val startTime: Date?,
    val endTime: Date?,
    val executionTime: Double?,
    val errorLevel: Int?,
    val output: String?) {

    enum class State (val text: String) {
        FINISHED ("finished"),
        RUNNING ("running")
    }
}