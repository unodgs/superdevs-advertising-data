package com.dgs.advertisingdata.repositories

import com.dgs.advertisingdata.model.AdvertisingCampaign
import com.dgs.advertisingdata.model.AdvertisingDataSource
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface AdvertisingCampaignRepository {
    @SqlUpdate("merge into advertising_campaign(name) key(name) values(:campaign.name)")
    fun save(campaign: AdvertisingCampaign)
    
    @SqlQuery("select id, name from advertising_campaign where name = :name")
    @RegisterConstructorMapper(AdvertisingCampaign::class)
    fun findByName(name: String): AdvertisingCampaign?

    @SqlQuery("select id, name from advertising_campaign order by name")
    @RegisterConstructorMapper(AdvertisingCampaign::class)
    fun findAll(): List<AdvertisingDataSource>

    @SqlUpdate("delete from advertising_campaign")
    fun removeAll()
}

