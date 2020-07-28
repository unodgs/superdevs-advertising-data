package com.dgs.advertisingdata.schdulers

import com.dgs.advertisingdata.services.AdvertisingCsvImportService
import io.micronaut.scheduling.annotation.Scheduled
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class ImportAdvertisingCsvScheduler constructor(
    private val advertisingCsvImportService: AdvertisingCsvImportService
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(initialDelay = "5s")
    internal fun importAdvertisingCsvFile() {
        log.info("Importing advertising csv file...")
        advertisingCsvImportService.downloadAdvertisingCsvFile()
    }
}