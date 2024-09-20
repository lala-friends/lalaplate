package org.lalafriends.lalaplate

import org.lalafriends.lalaplate.config.AppEnv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AppEnv::class)
class LalaplateApplication

fun main(args: Array<String>) {
    runApplication<LalaplateApplication>(*args)
}
