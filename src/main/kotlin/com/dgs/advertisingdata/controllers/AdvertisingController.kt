package com.dgs.advertisingdata.controllers

import com.dgs.advertisingdata.services.AdvertisingService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue

@Controller("/advertising")
class AdvertisingController(
    private val advertisingService: AdvertisingService
    ) {
    
    
    @Get("/data-sources", produces = [MediaType.APPLICATION_JSON])
    fun getDataSources(): Any {
        return advertisingService.getDataSources()
    }

    @Get("/campaigns/{dataSourceId}", produces = [MediaType.APPLICATION_JSON])
    fun getCampaigns(@QueryValue dataSourceId: Long): Any {
        return 0
    }
}