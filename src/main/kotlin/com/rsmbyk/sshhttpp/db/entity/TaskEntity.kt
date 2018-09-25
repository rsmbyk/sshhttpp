package com.rsmbyk.sshhttpp.db.entity

data class TaskEntity (
    val id: Int,
    val request: String,
    val command: String,
    val state: Int,
    val enqueueTime: Long,
    val startTime: Long?,
    val endTime: Long?,
    val executionTime: Double?,
    val errorLevel: Int?,
    val output: String?
): Entity