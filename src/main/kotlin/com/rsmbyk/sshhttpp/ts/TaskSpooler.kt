package com.rsmbyk.sshhttpp.ts

import com.rsmbyk.sshhttpp.model.Command
import com.rsmbyk.sshhttpp.model.Task
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

object TaskSpooler {

    private const val INFO_COMMAND = "Command"
    private const val INFO_STATE = "State"
    private const val INFO_ENQUEUE_TIME = "Enqueue time"
    private const val INFO_START_TIME = "Start time"
    private const val INFO_END_TIME = "End time"
    private const val INFO_EXECUTION_TIME = "Time run"
    private const val INFO_ERROR_LEVEL = "E-Level"
    private const val INFO_OUTPUT = "Output"

    private fun String.runCommand (): String {
        val process =
            ProcessBuilder (*split (" ").toTypedArray ()).start ()

        process.waitFor (5, TimeUnit.SECONDS)
        val output = BufferedReader (InputStreamReader (process.inputStream)).readText ()
        process.destroy ()
        return output.trim ()
    }

    fun add (command: Command): Int =
        "tsp $command".runCommand ().toInt ()

    private fun getTaskColumn (id: Int, column: Int? = null): String =
        "tsp".runCommand ()
            .split ("\n")
            .first { it.startsWith (id.toString ()) }
            .run {
                if (column != null && column in 0..5)
                    split(Regex("\\s+"), 6)[column-1]
                else
                    this
            }

    private fun getTaskInfo (id: Int): String =
        "tsp -i $id".runCommand ()

    private fun getTaskOutput (id: Int): String =
        "tsp -c $id".runCommand ()

    private fun extractTaskInfo (id: Int, info: String?): Map<String, String> {
        return if (info == null) {
            mapOf (
                INFO_STATE to Task.State.INVALID.name.toLowerCase())
        } else {
            info.split("\n")
                .asSequence ()
                .map { it.split (Pattern.compile (": "), 2) }
                .map { it[0] to it[1] }
                .toList ()
                .toMap ()
                .toMutableMap ()
                .apply {
                    getTaskColumn (id, 2).also {
                        put (INFO_STATE, it)
                        if (it == Task.State.FINISHED.name.toLowerCase ()) {
                            put (INFO_ERROR_LEVEL, getTaskColumn (id, 4))
                            put (INFO_OUTPUT, getTaskOutput (id))
                        }
                    }
                }
        }
    }


    fun getInfo (id: Int): TaskSpoolerInfo {
        return extractTaskInfo (id, getTaskInfo (id).takeUnless ("Error in wait_server_lines 2"::equals))
            .run {
                TaskSpoolerInfo (
                    id,
                    getValue (INFO_COMMAND),
                    getValue (INFO_STATE),
                    getValue (INFO_ENQUEUE_TIME),
                    get (INFO_START_TIME),
                    get (INFO_END_TIME),
                    get (INFO_EXECUTION_TIME),
                    get (INFO_ERROR_LEVEL)?.toInt(),
                    get (INFO_OUTPUT)
                )
            }
    }
}