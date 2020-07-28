package com.dgs.advertisingdata.services

import com.dgs.advertisingdata.config.DbConfig
import com.dgs.advertisingdata.models.dto.DateSampleDto
import com.dgs.advertisingdata.models.db.AdvertisingCampaign
import com.dgs.advertisingdata.models.db.AdvertisingDataSource
import com.dgs.advertisingdata.models.db.AdvertisingSample
import com.dgs.advertisingdata.repositories.AdvertisingCampaignRepository
import com.dgs.advertisingdata.repositories.AdvertisingDataSourceRepository
import com.dgs.advertisingdata.repositories.AdvertisingSampleRepository
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.jdbi.v3.sqlobject.transaction.Transaction
import org.slf4j.LoggerFactory
import java.time.LocalTime
import java.time.ZoneOffset
import javax.inject.Singleton

@Singleton
class AdvertisingDataService constructor(dbConfig: DbConfig) {
    private val db = dbConfig.jdbi
    private val log = LoggerFactory.getLogger(javaClass)

    @Transaction
    fun saveDataSource(dataSource: AdvertisingDataSource): AdvertisingDataSource {
        val dao = db.onDemand<AdvertisingDataSourceRepository>()
        dao.save(dataSource)
        return dao.findByName(dataSource.name)!!
    }
    
    @Transaction
    fun saveCampaign(campaign: AdvertisingCampaign): AdvertisingCampaign {
        val dao = db.onDemand<AdvertisingCampaignRepository>()
        dao.save(campaign)
        return dao.findByName(campaign.name)!!
    }

    fun saveSample(sample: AdvertisingSample): Long? {
        return db.onDemand<AdvertisingSampleRepository>().save(sample)
    }
    
    fun getDataSources(): List<AdvertisingDataSource> {
        return db.onDemand<AdvertisingDataSourceRepository>().findAll()
    }
    
    fun getCampaigns(dataSourceId: Long): List<AdvertisingCampaign> {
        return db.onDemand<AdvertisingCampaignRepository>().findAllByDataSource(dataSourceId)
    }
    
    fun getDataSamples(campaignIds: List<Long>): List<DateSampleDto> {
        return db.onDemand<AdvertisingSampleRepository>()
            .findSumsByCampaigns(campaignIds)
            .map {
                DateSampleDto(
                    it.sampleDate.toEpochSecond(LocalTime.MIN, ZoneOffset.UTC),
                    it.clicks,
                    it.impressions
                )
            }
    }
}