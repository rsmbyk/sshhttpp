package com.rsmbyk.sshhttpp.ts

import com.rsmbyk.sshhttpp.model.Command
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

object TaskSpooler {

    private const val INFO_COMMAND = "Command"
    private const val INFO_ENQUEUE_TIME = "Enqueue time"
    private const val INFO_START_TIME = "Start time"
    private const val INFO_END_TIME = "End time"
    private const val INFO_EXECUTION_TIME = "Time run"

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

    fun getInfo (id: Int): TaskSpoolerInfo {
        val map = getTaskInfo(id)
            .split ("\n")
            .asSequence ()
            .map { it.split (Pattern.compile (": "), 2) }
            .map { it[0] to it[1] }
            .toList ()
            .toMap ()

        val state = getTaskColumn (id, 2)
        val errorLevel = if (state == "finished") getTaskColumn(id, 4).toInt () else null
        val output = if (state == "finished") getTaskOutput(id) else null

        println (map)

        return TaskSpoolerInfo (
            id,
            map.getValue (INFO_COMMAND),
            state,
            map.getValue (INFO_ENQUEUE_TIME),
            map.get (INFO_START_TIME),
            map.get (INFO_END_TIME),
            map.get (INFO_EXECUTION_TIME),
            errorLevel,
            output
        )
    }
}