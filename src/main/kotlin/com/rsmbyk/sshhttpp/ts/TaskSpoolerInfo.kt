package com.rsmbyk.sshhttpp.ts

data class TaskSpoolerInfo (
    val id: Int,
    val command: String,
    val state: String,
    val enqueueTime: String,
    val startTime: String? = null,
    val endTime: String? = null,
    val executionTime: String? = null,
    val errorLevel: Int? = null,
    val output: String? = null
)