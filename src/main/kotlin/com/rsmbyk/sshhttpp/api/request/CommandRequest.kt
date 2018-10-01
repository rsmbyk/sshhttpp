package com.rsmbyk.sshhttpp.api.request

class CommandRequest (
    val type: String,
    val command: String,
    val subCommand: String?,
    val args: Array<ArgumentRequest>?)
        : Request
