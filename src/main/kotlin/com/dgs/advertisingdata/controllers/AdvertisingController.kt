package com.dgs.advertisingdata.controllers

import com.dgs.advertisingdata.models.dto.CampaignDto
import com.dgs.advertisingdata.models.dto.DataSourceDto
import com.dgs.advertisingdata.models.dto.DateSampleDto
import com.dgs.advertisingdata.services.AdvertisingDataService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import javax.annotation.Nullable

@Controller("/advertising")
class AdvertisingController(
    private val advertisingDataService: AdvertisingDataService
    ) {
    
    @Get("/data-sources", produces = [MediaType.APPLICATION_JSON])
    fun getDataSources(): List<DataSourceDto> {
        return advertisingDataService.getDataSources().map {
            DataSourceDto(it.id, it.name)
        }
    }

    @Get("/campaigns", produces = [MediaType.APPLICATION_JSON])
    fun getCampaigns(@Nullable @QueryValue dataSourceIds: List<Long>?): List<CampaignDto> {
        return advertisingDataService.getCampaigns(dataSourceIds).map {
            CampaignDto(it.id, it.name)
        }
    }
    
    @Post("/date-samples", produces = [MediaType.APPLICATION_JSON])
    fun getDateSamples(@Nullable @Body campaignIds: List<Long>?): List<DateSampleDto> {
        return advertisingDataService.getDateSamples(campaignIds)
    }
}