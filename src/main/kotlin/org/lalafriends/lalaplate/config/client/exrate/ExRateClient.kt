package org.lalafriends.lalaplate.config.client.exrate

interface ExRateClient {
    fun getExRate(currency: String): ExRateClientResources.Response.ExRateData
}
