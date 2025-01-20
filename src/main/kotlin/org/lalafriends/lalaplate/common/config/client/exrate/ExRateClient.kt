package org.lalafriends.lalaplate.common.config.client.exrate

interface ExRateClient {
    fun getExRate(currency: String): ExRateClientResources.Response.ExRateData
}
