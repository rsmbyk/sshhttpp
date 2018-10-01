package com.rsmbyk.sshhttpp.mapper

import com.rsmbyk.sshhttpp.api.request.ArgumentRequest
import com.rsmbyk.sshhttpp.api.request.CommandRequest
import com.rsmbyk.sshhttpp.api.request.RequestMapper
import com.rsmbyk.sshhttpp.model.Argument
import com.rsmbyk.sshhttpp.model.Command

class CommandRequestMapper (private val argumentMapper: RequestMapper<ArgumentRequest, Argument>)
    : RequestMapper<CommandRequest, Command> {

    override fun toModel (request: CommandRequest): Command {
        return request.run {
            Command (
                Command.Type.valueOf (type.toUpperCase ()),
                command,
                subCommand,
                args?.map (argumentMapper::toModel)?.toTypedArray()
            )
        }
    }
}
