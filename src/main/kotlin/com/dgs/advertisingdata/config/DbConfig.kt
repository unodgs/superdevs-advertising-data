package com.dgs.advertisingdata.config

import com.dgs.advertisingdata.loggers.QueryLogger
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.micronaut.context.annotation.ConfigurationInject
import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import org.jdbi.v3.core.Jdbi
import javax.inject.Singleton
import javax.sql.DataSource

@ConfigurationProperties("datasources.default")
data class DefaultDatabase @ConfigurationInject constructor(
    val url: String,
    val username: String,
    val password: String
)

@Singleton
class DbConfig(private val defaultDatabase: DefaultDatabase) {
    
    val jdbi = Jdbi.create(dataSource())

    init {
        jdbi.setSqlLogger(QueryLogger())
        jdbi.installPlugins()
    }
    
    @Factory
    @Replaces(DataSource::class)
    fun dataSource(): DataSource {
        val config = HikariConfig()
        config.jdbcUrl = defaultDatabase.url
        config.username = defaultDatabase.username
        config.password = defaultDatabase.password
        return HikariDataSource(config)
    }
}
