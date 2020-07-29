package com.dgs.advertisingdata.repositories

import com.dgs.advertisingdata.models.db.AdvertisingCampaign
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper
import org.jdbi.v3.sqlobject.customizer.BindList
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface AdvertisingCampaignRepository {
    @SqlUpdate("merge into advertising_campaign(name, data_source_id) key(name) values(:campaign.name, :campaign.dataSourceId)")
    fun save(campaign: AdvertisingCampaign)
    
    @SqlQuery("select id, data_source_id, name from advertising_campaign where name = :name")
    @RegisterConstructorMapper(AdvertisingCampaign::class)
    fun findByName(name: String): AdvertisingCampaign?

    @SqlQuery("""
        select id, data_source_id, name
          from advertising_campaign
         where data_source_id in (<dataSourceIds>)
      order by name
    """)
    @RegisterConstructorMapper(AdvertisingCampaign::class)
    fun findAllByDataSources(@BindList("dataSourceIds") dataSourceIds: List<Long>): List<AdvertisingCampaign>
    
    @SqlQuery("select id, data_source_id, name from advertising_campaign order by name")
    @RegisterConstructorMapper(AdvertisingCampaign::class)
    fun findAll(): List<AdvertisingCampaign>

    @SqlUpdate("delete from advertising_campaign")
    fun removeAll()
}

