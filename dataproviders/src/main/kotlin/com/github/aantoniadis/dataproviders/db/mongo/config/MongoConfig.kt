package com.github.aantoniadis.dataproviders.db.mongo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import java.io.IOException

@Configuration
@EnableMongoRepositories(basePackages = ["com.github.aantoniadis.dataproviders.db.mongo"])
class MongoConfig {
    companion object {
        private const val MONGO_DB_URL = "localhost"
        private const val MONGO_DB_NAME = "embeded_db"
    }

    @Bean
    @Throws(IOException::class)
    fun mongoTemplate(): MongoTemplate {
        val mongo = EmbeddedMongoFactoryBean().apply { setBindIp(MONGO_DB_URL) }
        val mongoClient = mongo.`object` ?: throw RuntimeException("Could not initialize mongo client")
        return MongoTemplate(mongoClient, MONGO_DB_NAME)
    }

}