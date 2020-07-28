package com.dgs.advertisingdata.model

import java.time.LocalDate

data class AdvertisingSample(
    val sampleDate: LocalDate,
    val dataSource: AdvertisingDataSource,
    val campaign: AdvertisingCampaign,
    val clicks: Int,
    val impressions: Int
)