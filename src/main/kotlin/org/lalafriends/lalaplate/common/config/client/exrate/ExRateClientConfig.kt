package org.lalafriends.lalaplate.common.config.client.exrate

import org.lalafriends.lalaplate.common.config.AppEnv
import org.lalafriends.lalaplate.common.util.RestClientUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ExRateClientConfig(
    private val appEnv: AppEnv,
) {
    @Bean
    fun exRateClient(): ExRateClient {
        val env = appEnv.client.exrate
        return if (env.useDummy) {
            ExRateDummyClient()
        } else {
            ExRateStableClient(env, RestClientUtil.from(env))
        }
    }
}
