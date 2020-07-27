package com.dgs.advertisingdata.repositories

import com.dgs.advertisingdata.model.AdvertisingCampaign
import com.dgs.advertisingdata.model.AdvertisingDataSource
import com.dgs.advertisingdata.model.AdvertisingSample
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface AdvertisingRepository {
    @SqlUpdate("insert into advertising_data_source(name) values(:dataSource.name)")
    @GetGeneratedKeys
    fun saveDataSource(dataSource: AdvertisingDataSource): Long

    @SqlUpdate("insert into advertising_campaign(name) values(:campaign.name)")
    @GetGeneratedKeys
    fun saveCampaign(campaign: AdvertisingCampaign): Long

    @SqlUpdate("""insert into advertising_sample(data_source_id, campaign_id, clicks, impressions)
        values(:sample.dataSource.id, :sample.campaign.id, :sample.clicks, :sample.impressions)""")
    @GetGeneratedKeys
    fun saveSample(sample: AdvertisingSample): Long
    
    @SqlQuery("select id, name from advertising_data_source order by name")
    fun findAllDataSources(): List<AdvertisingDataSource>

//    @SqlQuery("""select * from client""")
//    @RegisterConstructorMapper(Client::class)
//    fun findAll(): List<Client>
//
//    @SqlQuery("""select * from client where id = :id""")
//    @RegisterConstructorMapper(Client::class)
//    fun findById(@Bind("id") id: Long): Client?
//    
//    @SqlUpdate("""delete from client where id = :id""")
//    fun remove(@Bind("id") id: Long)
//
//    @SqlUpdate("""delete from advertisin""")
//    fun removeAll()
}

