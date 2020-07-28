package com.dgs.advertisingdata.services

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxStreamingHttpClient
import io.reactivex.Maybe
import javax.inject.Singleton

@Singleton
class DownloadService constructor(
    private val httpClient: RxStreamingHttpClient
) {
    fun downloadFile(fileUri: String): Maybe<String> {
        val req = HttpRequest.GET<Any>(fileUri)
        return httpClient.retrieve(req).firstElement()
    }
}