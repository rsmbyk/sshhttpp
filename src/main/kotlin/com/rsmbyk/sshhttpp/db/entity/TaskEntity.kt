package com.rsmbyk.sshhttpp.db.entity

import org.bson.types.ObjectId

data class TaskEntity (
    val _id: ObjectId,
    val state: Int,
    val command: String,
    val enqueueTime: Long?,
    val startTime: Long?,
    val endTime: Long?,
    val executionTime: Double?,
    val errorLevel: Int?,
    val output: String?
): Entity