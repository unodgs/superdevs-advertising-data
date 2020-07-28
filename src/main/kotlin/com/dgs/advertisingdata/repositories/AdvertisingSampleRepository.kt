package com.dgs.advertisingdata.repositories

import com.dgs.advertisingdata.model.AdvertisingSample
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface AdvertisingSampleRepository {
    @SqlUpdate("""insert into advertising_sample(sample_date, data_source_id, campaign_id, clicks, impressions)
        values(:sample.sampleDate, :sample.dataSource.id, :sample.campaign.id, :sample.clicks, :sample.impressions)""")
    @GetGeneratedKeys
    fun save(sample: AdvertisingSample): Long
    
    @SqlUpdate("delete from advertising_sample")
    fun removeAll()
}

