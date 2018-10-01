package com.rsmbyk.sshhttpp.ts.entity

class JobInput (
    override val label: String,
    override val command: String,
    override val originalCommand: String)
        : Job
