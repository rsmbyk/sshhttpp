package com.rsmbyk.sshhttpp.di

import com.rsmbyk.sshhttpp.App
import com.rsmbyk.sshhttpp.di.module.*
import dagger.Component

@Component (modules = [
    AppModule::class,
    HandlerModule::class,
    MongoModule::class,
    TaskSpoolerModule::class,
    MapperModule::class])
interface AppComponent {

    fun poke (app: App)
}