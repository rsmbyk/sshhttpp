package com.rsmbyk.sshhttpp.di.module

import com.rsmbyk.sshhttpp.model.Prop
import dagger.Module
import dagger.Provides
import java.util.*

@Module
class AppModule {

    companion object {
        private const val PROPERTIES_FILE_NAME = "sshhttpp.properties"
    }

    @Provides
    fun provideProperties (): Properties {
        return Properties ().apply {
            load (
                javaClass.classLoader.getResourceAsStream (PROPERTIES_FILE_NAME))
        }
    }

    @Provides
    fun provideProp (prop: Properties): Prop =
        Prop (prop)
}
