package org.lalafriends.lalaplate.config.client.exrate

import java.math.BigDecimal

class ExRateClientResources {
    class Response {
        data class ExRateData(
            val result: String,
            val rates: Map<String, BigDecimal>,
        )
    }
}
