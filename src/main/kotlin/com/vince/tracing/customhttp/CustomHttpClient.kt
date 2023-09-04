package com.vince.tracing.customhttp

import org.apache.http.HttpRequest
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Component
class CustomHttpClient {

    // Create an HttpClient instance
    val httpClient = HttpClients.createDefault()

    fun get(req: HttpRequest): String {

        // Create a GET request object


        try {
            // Execute the request and get the response
            val response = httpClient.execute(req as HttpUriRequest?)

            // Get the response entity
            val entity = response.entity

            // Use EntityUtils to get the response content as a string
            return EntityUtils.toString(entity)

        } catch (e: Exception) {
            // Handle exceptions
            e.printStackTrace()
            throw e
        } finally {
            // Close the HttpClient
            httpClient.close()
        }
    }
}