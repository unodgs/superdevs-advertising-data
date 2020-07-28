package com.dgs.advertisingdata.repositories

import com.dgs.advertisingdata.model.AdvertisingDataSource
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface AdvertisingDataSourceRepository {
    @SqlUpdate("merge into advertising_data_source(name) key(name) values(:dataSource.name)")
    fun save(dataSource: AdvertisingDataSource)
    
    @SqlQuery("select id, name from advertising_data_source order by name")
    @RegisterConstructorMapper(AdvertisingDataSource::class)
    fun findAll(): List<AdvertisingDataSource>

    @SqlQuery("select id, name from advertising_data_source where name = :name")
    @RegisterConstructorMapper(AdvertisingDataSource::class)
    fun findByName(name: String): AdvertisingDataSource?

    @SqlUpdate("delete from advertising_data_source")
    fun deleteAll()
}

