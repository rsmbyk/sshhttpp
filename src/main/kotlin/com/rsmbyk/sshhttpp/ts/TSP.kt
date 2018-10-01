package com.rsmbyk.sshhttpp.ts

import com.rsmbyk.sshhttpp.model.Task
import com.rsmbyk.sshhttpp.ts.entity.JobInput
import com.rsmbyk.sshhttpp.ts.entity.JobOutput
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class TSP {

    companion object {
        const val LABEL_PREFIX = "rsmbyk-"

        private const val INFO_COMMAND = "Command"
        private const val INFO_STATE = "State"
        private const val INFO_ENQUEUE_TIME = "Enqueue time"
        private const val INFO_START_TIME = "Start time"
        private const val INFO_END_TIME = "End time"
        private const val INFO_EXECUTION_TIME = "Time run"
        private const val INFO_ERROR_LEVEL = "E-Level"
        private const val INFO_OUTPUT = "Output"

        private val REGEX_WHITESPACES = Regex ("\\s+")
    }

    private fun String.runCommand (): String {
        val process =
            ProcessBuilder (*split (" ").toTypedArray ()).start ()

        process.waitFor (5, TimeUnit.SECONDS)
        val output = BufferedReader (InputStreamReader (process.inputStream)).readText ()
        process.destroy ()
        return output.trim ()
    }

    private fun tsp (action: Char?, vararg args: Any): String {
        var command = "tsp"
        if (action != null && action.isLetter ())
            command = "$command -$action"
        if (args.isNotEmpty ())
            command = command.plus (args.joinToString(" ", " "))
        return command.runCommand ()
    }

    private fun getTaskList (): List<String> =
        tsp ('l').split ("\n").drop (1)

    fun add (input: JobInput): JobOutput {
        tsp('L', input.label, input.command).toInt()
        return input.run {
            JobOutput (
                input.label,
                "enqueued",
                command,
                originalCommand,
                null,
                null,
                null,
                null,
                null,
                null
            )
        }
    }

    private fun getTask (label: String): JobInfo? =
        getTaskList ()
            .asSequence ()
            .map (::JobInfo)
            .firstOrNull { it.command.startsWith ("[$label]") }

    private fun getTaskInfo (id: Int): String =
        tsp ('i', id)

    private fun getTaskOutput (id: Int): String =
        tsp ('c', id)

    private fun removeTask (id: Int): String =
        tsp ('r', id)

    private fun extractJobInfo (label: String): Map<String, String> {
        val jobInfo = getTask (label)
        return if (jobInfo == null) {
            mapOf (INFO_STATE to "invalid")
        } else {
            getTaskInfo (jobInfo.id)
                .split ("\n")
                .asSequence ()
                .map { it.split (": ", limit = 2) }
                .map { it[0] to it[1] }
                .toList ()
                .toMap ()
                .toMutableMap ()
                .apply {
                    put (INFO_STATE, jobInfo.state)

                    if (jobInfo.state == "finished") {
                        put (INFO_ERROR_LEVEL, jobInfo.errorLevel.toString ())
                        put (INFO_OUTPUT, getTaskOutput (jobInfo.id))
                        removeTask (jobInfo.id)
                    }
                }
        }
    }

    fun get (input: JobInput): JobOutput {
        return extractJobInfo (input.label).run {
            JobOutput (
                input.label,
                getValue (INFO_STATE),
                input.command,
                input.originalCommand,
                get (INFO_ENQUEUE_TIME),
                get (INFO_START_TIME),
                get (INFO_END_TIME),
                get (INFO_EXECUTION_TIME),
                get (INFO_ERROR_LEVEL)?.toInt(),
                get (INFO_OUTPUT)
            )
        }
    }

    private class JobInfo (row: String) {

        val id: Int
        val state: String
        val output: String?
        val errorLevel: Int?
        val times: String?
        val command: String

        init {
            val columns = row.split (REGEX_WHITESPACES, 4)
            id = columns.first ().toInt ()
            state = columns[1]
            output = columns[2]

            if (state == Task.State.FINISHED.name.toLowerCase ()) {
                val remainingColumns = columns[3].split (REGEX_WHITESPACES, 3)
                errorLevel = remainingColumns.first ().toInt ()
                times = remainingColumns[1]
                command = remainingColumns.last ()
            }
            else {
                errorLevel = null
                times = null
                command = columns.last ()
            }
        }
    }
}