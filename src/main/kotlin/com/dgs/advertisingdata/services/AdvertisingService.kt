package com.dgs.advertisingdata.services

import com.dgs.advertisingdata.config.DbConfig
import com.dgs.advertisingdata.model.AdvertisingCampaign
import com.dgs.advertisingdata.model.AdvertisingDataSource
import com.dgs.advertisingdata.model.AdvertisingSample
import com.dgs.advertisingdata.repositories.AdvertisingCampaignRepository
import com.dgs.advertisingdata.repositories.AdvertisingDataSourceRepository
import com.dgs.advertisingdata.repositories.AdvertisingSampleRepository
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.jdbi.v3.sqlobject.transaction.Transaction
import javax.inject.Singleton

@Singleton
class AdvertisingService constructor(dbConfig: DbConfig) {
    private val db = dbConfig.jdbi

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
        return try {
            db.onDemand<AdvertisingDataSourceRepository>().findAll()
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    fun getCampaigns(dataSourceId: Long): List<AdvertisingCampaign> {
        return try {
            db.onDemand<AdvertisingCampaignRepository>().findAllByDataSource(dataSourceId)
        } catch (e: Exception) {
            emptyList()
        }
    }
}