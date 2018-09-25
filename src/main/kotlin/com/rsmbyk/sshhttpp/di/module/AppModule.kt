package com.rsmbyk.sshhttpp.di.module

import com.rsmbyk.sshhttpp.PROPERTIES
import com.rsmbyk.sshhttpp.model.Prop
import dagger.Module
import dagger.Provides
import java.io.InputStream
import java.util.*
import javax.inject.Named

@Module
class AppModule {

    companion object {
        private const val PROPERTIES_FILE_NAME = "sshhttpp.properties"
    }

    @Provides
    @Named (PROPERTIES)
    fun providePropertiesInputStream (): InputStream =
        javaClass.classLoader.getResourceAsStream (PROPERTIES_FILE_NAME)

    @Provides
    fun provideProperties (@Named (PROPERTIES) properties: InputStream): Properties {
        return Properties ().apply {
            load (properties)
        }
    }

    @Provides
    fun provideProp (prop: Properties): Prop =
        Prop (prop)
}