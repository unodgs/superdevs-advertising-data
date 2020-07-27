package com.dgs.advertisingdata.model

import java.time.LocalDate

data class AdvertisingSample(
    val createdAt: LocalDate,
    val campaign: AdvertisingCampaign,
    val dataSource: AdvertisingDataSource,
    val clicks: Int,
    val impressions: Int
)