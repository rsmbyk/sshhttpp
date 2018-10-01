package com.rsmbyk.sshhttpp.di.module

import com.fasterxml.jackson.databind.ObjectMapper
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.rsmbyk.sshhttpp.db.entity.Codec
import com.rsmbyk.sshhttpp.db.codec.TaskEntityCodec
import com.rsmbyk.sshhttpp.db.dao.TaskDao
import com.rsmbyk.sshhttpp.db.entity.TaskEntity
import com.rsmbyk.sshhttpp.model.Prop
import dagger.Module
import dagger.Provides
import org.bson.Document

@Module
class MongoModule {

    @Provides
    fun provideConnectionString (prop: Prop): ConnectionString =
        ConnectionString (
            "mongodb://${prop.DATABASE_HOST}:${prop.DATABASE_PORT}")

    @Provides
    fun provideMongoCredential (prop: Prop): MongoCredential =
        MongoCredential.createCredential (
            prop.DATABASE_USERNAME,
            prop.DATABASE_NAME,
            prop.DATABASE_PASSWORD.toCharArray ())

    @Provides
    fun provideMongoClientSettings (conn: ConnectionString, credential: MongoCredential): MongoClientSettings =
        MongoClientSettings.builder ()
            .applyConnectionString (conn)
//            .credential (credential)
            .build ()

    @Provides
    fun provideMongoClient (settings: MongoClientSettings): MongoClient =
        MongoClients.create (settings)

    @Provides
    fun provideAppDatabase (client: MongoClient, prop: Prop): MongoDatabase =
        client.getDatabase (prop.DATABASE_NAME)

    @Provides
    fun provideTaskCollection (db: MongoDatabase, prop: Prop): MongoCollection<Document> =
        db.getCollection (prop.TASK_COLLECTION_NAME)

    @Provides
    fun provideTaskCodec (): Codec<TaskEntity> =
        TaskEntityCodec ()

    @Provides
    fun provideTaskDao (col: MongoCollection<Document>, codec: Codec<TaskEntity>, mapper: ObjectMapper) =
            TaskDao(col, codec, mapper)
}