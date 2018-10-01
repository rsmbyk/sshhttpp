package com.rsmbyk.sshhttpp

import com.rsmbyk.sshhttpp.controller.TaskController
import com.rsmbyk.sshhttpp.di.DaggerAppComponent
import com.rsmbyk.sshhttpp.handler.Handler
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import javax.inject.Inject

class App {

    @Inject
    lateinit var handler: Handler

    @Inject
    lateinit var taskController: TaskController

    fun start () {
        val app = Javalin.create ().enableDebugLogging ().start (58534)
        app.before (handler::checkMagicWord)
        app.routes {
            path (PATH_ROOT) {

            }
            path (PATH_TASKS) {
                get (taskController::all)
                post (taskController::create)
                delete (taskController::drop)
                path (PATH_TASKS_ID) {
                    get (taskController::get)
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun main (args: Array<String>) {
            val app = App ()
            DaggerAppComponent.create ().poke (app)
            app.start ()
        }
    }
}