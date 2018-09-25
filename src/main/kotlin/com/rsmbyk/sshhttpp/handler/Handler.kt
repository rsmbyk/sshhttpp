package com.rsmbyk.sshhttpp.handler

import com.rsmbyk.sshhttpp.model.Prop
import io.javalin.Context
import io.javalin.ForbiddenResponse

class Handler (private val prop: Prop) {

    fun checkMagicWord (context: Context) {
        if (context.header (prop.MAGIC_WORD_HEADER_NAME) != prop.MAGIC_WORD)
            throw ForbiddenResponse ("Invalid magic word!")
    }
}