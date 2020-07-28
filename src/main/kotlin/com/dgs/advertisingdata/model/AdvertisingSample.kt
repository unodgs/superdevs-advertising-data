package com.dgs.advertisingdata.model

import java.time.LocalDate

data class AdvertisingSample(
    val id: Long,
    val sampleDate: LocalDate,
    val dataSource: AdvertisingDataSource,
    val campaign: AdvertisingCampaign,
    val clicks: Int,
    val impressions: Int
)