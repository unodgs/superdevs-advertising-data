package com.dgs.advertisingdata.schdulers

import com.dgs.advertisingdata.model.AdvertisingCampaign
import com.dgs.advertisingdata.model.AdvertisingDataSource
import com.dgs.advertisingdata.model.AdvertisingSample
import com.dgs.advertisingdata.services.AdvertisingService
import com.dgs.advertisingdata.services.CsvParserService
import com.dgs.advertisingdata.services.DownloadService
import io.micronaut.context.annotation.Property
import io.micronaut.scheduling.annotation.Scheduled
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

@Singleton
class DownloadScheduler constructor(
    private val downloadService: DownloadService,
    private val csvParserService: CsvParserService,
    private val advertisingService: AdvertisingService,
    @Property(name = "advertising-data-source.uri") private val fileUri: String
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(initialDelay = "5s")
    internal fun downloadAdvertisingCsvFile() {
        var skippedLines = 0
        var importedLines = 0
        
        log.info("Downloading {}", fileUri)
        
        downloadService.downloadFile(fileUri).subscribe { csvData ->
            log.info("Parsing csv file...")
    
            val csvDatePattern = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            
            csvParserService.parse(csvData) { line, data ->
                try {
                    val csvDataSource = data["Datasource"]
                    val csvCampaign = data["Campaign"]
                    val csvDate = data["Date"]
                    val csvClicks = data["Clicks"]
                    val csvImpressions = data["Impressions"]
                    
                    if (csvDataSource != null && csvCampaign != null && csvDate != null && csvClicks != null && csvImpressions != null) {
    
                        val dataSource = advertisingService.saveDataSource(
                            AdvertisingDataSource(name = csvDataSource, id = null)
                        )
                        val campaign = advertisingService.saveCampaign(
                            AdvertisingCampaign(name = csvCampaign, id = null)
                        )
                        val sampleDate = LocalDate.parse(csvDate, csvDatePattern)
                        
                        val clicks = if (csvClicks.isEmpty()) 0 else csvClicks.toInt()
                        val impressions = if (csvImpressions.isEmpty()) 0 else csvImpressions.toInt()
    
                        advertisingService.saveSample(
                            AdvertisingSample(
                                sampleDate,
                                dataSource, campaign,
                                clicks,
                                impressions
                            )
                        )
                        
                        importedLines += 1
                        
                        if (importedLines % 500 == 0) {
                            log.debug("Imported {} lines", importedLines)
                        }
                    }
                } catch (e: Exception) {
                    log.error("Error importing line {} of {}", line + 1, fileUri, e)
                    skippedLines += 1
                }
            }
            
            log.info("Imported {} lines, skipped {}", importedLines, skippedLines)
        }
    }
}