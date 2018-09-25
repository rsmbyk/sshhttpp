package com.rsmbyk.sshhttpp.di

import com.rsmbyk.sshhttpp.App
import com.rsmbyk.sshhttpp.di.module.AppModule
import com.rsmbyk.sshhttpp.di.module.HandlerModule
import com.rsmbyk.sshhttpp.di.module.MapperModule
import com.rsmbyk.sshhttpp.di.module.MongoModule
import dagger.Component

@Component (modules = [
    AppModule::class,
    MongoModule::class,
    HandlerModule::class,
    MapperModule::class])
interface AppComponent {

    fun poke (app: App)
}