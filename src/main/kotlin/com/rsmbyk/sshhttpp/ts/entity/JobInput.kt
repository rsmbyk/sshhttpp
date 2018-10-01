package com.rsmbyk.sshhttpp.ts.entity

data class JobInput (
    override val label: String,
    override val command: String,
    override val originalCommand: String)
        : Job