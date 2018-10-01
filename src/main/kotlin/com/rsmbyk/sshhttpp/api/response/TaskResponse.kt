package com.rsmbyk.sshhttpp.api.response

import java.util.*

class TaskResponse (
    val id: String,
    val state: Int,
    val command: String,
    val enqueueTime: Date?,
    val startTime: Date?,
    val endTime: Date?,
    val executionTime: Double?,
    val errorLevel: Int?,
    val output: String?
): Response