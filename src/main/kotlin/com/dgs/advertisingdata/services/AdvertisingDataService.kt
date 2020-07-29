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
import java.time.format.DateTimeFormatter
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
    
    fun getCampaigns(dataSourceIds: List<Long>?): List<AdvertisingCampaign> {
        val dao = db.onDemand<AdvertisingCampaignRepository>()
        return if (dataSourceIds.isNullOrEmpty())
            dao.findAll()
        else
            dao.findAllByDataSources(dataSourceIds)
    }
    
    fun getDateSamples(campaignIds: List<Long>?): List<DateSampleDto> {
        val dao = db.onDemand<AdvertisingSampleRepository>()

        val samples = if (campaignIds.isNullOrEmpty())
            dao.findAllSums()
        else
            dao.findSumsByCampaigns(campaignIds)
                
        return samples.map {
            DateSampleDto(
                it.sampleDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                it.clicks,
                it.impressions
            )
        }
    }
}