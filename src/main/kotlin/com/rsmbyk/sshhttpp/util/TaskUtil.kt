package com.rsmbyk.sshhttpp.util

import com.rsmbyk.sshhttpp.model.Argument
import java.text.SimpleDateFormat
import java.util.*

fun Array<Argument>.construct (): String =
    joinToString (" ", transform = Argument::toString)

fun Long.toDate (): Date {
    val calendar = Calendar.getInstance ()
    calendar.timeInMillis = this
    return calendar.time
}

fun String.toDate (): Date =
    SimpleDateFormat.getDateTimeInstance ().parse (this)

val Any?.nonNullText: String
    get () = if (this == null) "" else toString ()