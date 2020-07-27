package com.dgs.advertisingdata.services

import com.dgs.advertisingdata.config.DbConfig
import com.dgs.advertisingdata.model.AdvertisingCampaign
import com.dgs.advertisingdata.model.AdvertisingDataSource
import com.dgs.advertisingdata.model.AdvertisingSample
import com.dgs.advertisingdata.repositories.AdvertisingRepository
import org.jdbi.v3.sqlobject.kotlin.onDemand
import javax.inject.Singleton

@Singleton
class AdvertisingService constructor(dbConfig: DbConfig) {
    private val db = dbConfig.jdbi

    fun saveDataSource(dataSource: AdvertisingDataSource): Long? {
        return try {
            db.onDemand<AdvertisingRepository>().saveDataSource(dataSource)
        } catch (e: Exception) {
            null
        }
    }
    
    fun saveCampaign(campaign: AdvertisingCampaign): Long? {
        return try {
            db.onDemand<AdvertisingRepository>().saveCampaign(campaign)
        } catch (e: Exception) {
            null
        }
    }

    fun saveSample(sample: AdvertisingSample): Long? {
        return try {
            db.onDemand<AdvertisingRepository>().saveSample(sample)
        } catch (e: Exception) {
            null
        }
    }
    
    fun getDataSources(): List<AdvertisingDataSource> {
        return try {
            db.onDemand<AdvertisingRepository>().findAllDataSources()
        } catch (e: Exception) {
            emptyList()
        }
    }
}