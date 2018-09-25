package com.rsmbyk.sshhttpp.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.rsmbyk.sshhttpp.util.construct
import com.rsmbyk.sshhttpp.util.nonNullText
import java.util.*

data class Command (val type: Type, val command: String, val subCommand: String?, val args: Array<Argument>?) {

    enum class Type {
        @JsonProperty ("string")
        STRING,
        @JsonProperty ("command")
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

    override fun equals (other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Command

        if (type != other.type) return false
        if (command != other.command) return false
        if (subCommand != other.subCommand) return false
        if (!Arrays.equals (args, other.args)) return false

        return true
    }

    override fun hashCode (): Int {
        var result = type.hashCode ()
        result = 31 * result + command.hashCode ()
        result = 31 * result + (subCommand?.hashCode () ?: 0)
        result = 31 * result + (args?.let { Arrays.hashCode (it) } ?: 0)
        return result
    }
}