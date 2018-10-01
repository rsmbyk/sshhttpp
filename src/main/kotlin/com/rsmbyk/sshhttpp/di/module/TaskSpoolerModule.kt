package com.rsmbyk.sshhttpp.di.module

import com.rsmbyk.sshhttpp.ts.TSP
import dagger.Module
import dagger.Provides

@Module
class TaskSpoolerModule {

    @Provides
    fun provideTaskSpooler (): TSP =
        TSP ()
}
