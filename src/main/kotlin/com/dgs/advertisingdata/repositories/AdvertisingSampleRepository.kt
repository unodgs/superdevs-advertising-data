package com.dgs.advertisingdata.repositories

import com.dgs.advertisingdata.models.db.AdvertisingSample
import com.dgs.advertisingdata.models.db.DateSample
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper
import org.jdbi.v3.sqlobject.customizer.BindList
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface AdvertisingSampleRepository {
    @SqlUpdate("""insert into advertising_sample(sample_date, data_source_id, campaign_id, clicks, impressions)
        values(:sample.sampleDate, :sample.dataSource.id, :sample.campaign.id, :sample.clicks, :sample.impressions)""")
    @GetGeneratedKeys
    fun save(sample: AdvertisingSample): Long
    
    @SqlUpdate("delete from advertising_sample")
    fun removeAll()
    
    @SqlQuery("""
        select sample_date, sum(clicks) as clicks, sum(impressions) as impressions
          from advertising_sample
         where campaign_id in (<campaignIds>)
      group by sample_date
      order by sample_date
    """)
    @RegisterConstructorMapper(DateSample::class)
    fun findSumsByCampaigns(@BindList("campaignIds") campaignIds: List<Long>): List<DateSample>

}

