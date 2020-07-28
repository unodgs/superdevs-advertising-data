package com.dgs.advertisingdata.services

import com.dgs.advertisingdata.models.db.AdvertisingCampaign
import com.dgs.advertisingdata.models.db.AdvertisingDataSource
import com.dgs.advertisingdata.models.db.AdvertisingSample
import io.micronaut.context.annotation.Property
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

@Singleton
class AdvertisingCsvImportService constructor(
    private val downloadFileService: DownloadFileService,
    private val csvParserService: CsvParserService,
    private val advertisingDataService: AdvertisingDataService,
    @Property(name = "advertising-data-source.uri") private val fileUri: String
) {
    private val log = LoggerFactory.getLogger(javaClass)
    
    fun downloadAdvertisingCsvFile() {
        var skippedLines = 0
        var importedLines = 0
        
        log.info("Downloading {}", fileUri)
        
        downloadFileService.downloadFile(fileUri).subscribe par@{ csvData ->
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
                        
                        val dataSource = advertisingDataService.saveDataSource(
                            AdvertisingDataSource(name = csvDataSource, id = 0)
                        )
                        val campaign = advertisingDataService.saveCampaign(
                            AdvertisingCampaign(name = csvCampaign, id = 0, dataSourceId = dataSource.id)
                        )
                        val sampleDate = LocalDate.parse(csvDate, csvDatePattern)
                        
                        val clicks = if (csvClicks.isEmpty()) 0 else csvClicks.toInt()
                        val impressions = if (csvImpressions.isEmpty()) 0 else csvImpressions.toInt()
                        
                        advertisingDataService.saveSample(
                            AdvertisingSample(
                                0,
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