package com.rsmbyk.sshhttpp.model

import com.rsmbyk.sshhttpp.util.construct
import com.rsmbyk.sshhttpp.util.nonNullText

class Command (
    private val type: Type,
    private val command: String,
    private val subCommand: String?,
    private val args: Array<Argument>?) {

    enum class Type {
        STRING,
        COMMAND
    }

    override fun toString (): String {
        return when (type) {
            Type.STRING -> command
            Type.COMMAND ->
                "$command ${subCommand.nonNullText} ${args?.construct ()}"
                    .replace ("\\s+".toRegex (), " ")
        }
    }
}
