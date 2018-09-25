package com.rsmbyk.sshhttpp.model

import java.util.*


class Prop (private val prop: Properties) {

    companion object {
        const val DATABASE_HOST = "DATABASE_HOST"
        const val DATABASE_PORT = "DATABASE_PORT"
        const val DATABASE_NAME = "DATABASE_NAME"
        const val DATABASE_USERNAME = "DATABASE_USERNAME"
        const val DATABASE_PASSWORD = "DATABASE_PASSWORD"
        const val TASK_COLLECTION_NAME = "TASK_COLLECTION_NAME"
        const val MAGIC_WORD_HEADER_NAME = "MAGIC_WORD_HEADER_NAME"
        const val MAGIC_WORD = "MAGIC_WORD"
    }

    val DATABASE_HOST: String
        get () = prop.getProperty (Companion.DATABASE_HOST)

    val DATABASE_PORT: Int
        get () = prop.getProperty (Companion.DATABASE_PORT).toInt ()

    val DATABASE_NAME: String
        get () = prop.getProperty (Companion.DATABASE_NAME)

    val DATABASE_USERNAME: String
        get () = prop.getProperty (Companion.DATABASE_USERNAME)

    val DATABASE_PASSWORD: String
        get () = prop.getProperty (Companion.DATABASE_PASSWORD)

    val TASK_COLLECTION_NAME: String
        get () = prop.getProperty (Companion.TASK_COLLECTION_NAME)

    val MAGIC_WORD: String
        get () = prop.getProperty (Companion.MAGIC_WORD)

    val MAGIC_WORD_HEADER_NAME: String
        get () = prop.getProperty (Companion.MAGIC_WORD_HEADER_NAME)
}