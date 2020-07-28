package com.dgs.advertisingdata.services

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import javax.inject.Singleton

@Singleton
class CsvParserService {
    fun parse(csvData: String, consumer: (line: Int, data: Map<String, String>) -> Unit) {
        csvReader().open(csvData.byteInputStream()) {
            readAllWithHeaderAsSequence().forEachIndexed { index, data ->
                consumer(index, data)
            }
        }
    }
}
