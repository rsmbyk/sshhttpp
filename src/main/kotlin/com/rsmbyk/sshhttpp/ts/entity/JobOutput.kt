package com.rsmbyk.sshhttpp.ts.entity

class JobOutput (
    override val label: String,
    val state: String,
    override val command: String,
    override val originalCommand: String,
    val enqueueTime: String?,
    val startTime: String?,
    val endTime: String?,
    val executionTime: String?,
    val errorLevel: Int?,
    val output: String?)
        : Job
