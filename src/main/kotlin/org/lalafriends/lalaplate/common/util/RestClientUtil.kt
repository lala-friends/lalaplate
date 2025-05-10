package org.lalafriends.lalaplate.common.util

import org.lalafriends.lalaplate.common.config.AppEnv
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.ClientHttpRequestFactories
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestClient
import java.net.URI
import java.time.Duration

/**
 * RestClient를 손쉽게 생성하기 위한 유틸리티
 */
class RestClientUtil private constructor(
    private val baseUrl: String,
    private val timeout: Duration,
    private val headers: Map<String, String>,
) {
    private val logger = LoggerFactory.getLogger(RestClientUtil::class.java)

    /**
     * 설정된 파라미터로 RestClient를 생성합니다.
     */
    fun build(): RestClient {
        logger.debug("Creating RestClient for: {}", baseUrl)

        val clientHttpRequestFactory =
            ClientHttpRequestFactories.get(
                ClientHttpRequestFactorySettings.DEFAULTS
                    .withReadTimeout(timeout)
                    .withConnectTimeout(timeout),
            )

        return RestClient
            .builder()
            .baseUrl(baseUrl)
            .requestFactory(clientHttpRequestFactory)
            .defaultHeaders { httpHeaders ->
                headers.forEach { (name, value) ->
                    httpHeaders.add(name, value)
                }
            }.build()
    }

    /**
     * RestClientUtil 빌더 클래스
     */
    class Builder {
        private var baseUrl: String = ""
        private var timeout: Duration = Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS)
        private val headers: MutableMap<String, String> = DEFAULT_HEADERS.toMutableMap()

        /**
         * 기본 URL을 설정합니다.
         */
        fun baseUrl(url: String): Builder {
            this.baseUrl = url
            return this
        }

        /**
         * 타임아웃 값을 설정합니다.
         */
        fun timeout(seconds: Long): Builder {
            this.timeout = Duration.ofSeconds(seconds)
            return this
        }

        /**
         * 타임아웃 값을 Duration으로 설정합니다.
         */
        fun timeout(duration: Duration): Builder {
            this.timeout = duration
            return this
        }

        /**
         * 헤더를 추가합니다.
         */
        fun header(
            name: String,
            value: String,
        ): Builder {
            this.headers[name] = value
            return this
        }

        /**
         * 여러 헤더를 한 번에 추가합니다.
         */
        fun headers(additionalHeaders: Map<String, String>): Builder {
            this.headers.putAll(additionalHeaders)
            return this
        }

        /**
         * RestClientUtil 인스턴스를 생성합니다.
         */
        fun create(): RestClientUtil {
            require(baseUrl.isNotBlank()) { "Base URL must not be blank" }

            // URL 유효성 검사
            try {
                URI(baseUrl)
            } catch (e: Exception) {
                throw IllegalArgumentException("Invalid base URL: $baseUrl", e)
            }

            return RestClientUtil(baseUrl, timeout, headers)
        }
    }

    companion object {
        private const val DEFAULT_TIMEOUT_SECONDS = 30L

        private val DEFAULT_HEADERS =
            mapOf(
                HttpHeaders.CONTENT_TYPE to MediaType.APPLICATION_JSON_VALUE,
                HttpHeaders.ACCEPT to MediaType.APPLICATION_JSON_VALUE,
            )

        /**
         * 빌더 인스턴스를 생성합니다.
         */
        @JvmStatic
        fun builder(): Builder = Builder()

        /**
         * ConnectionInfo를 사용하여 RestClient를 생성합니다.
         */
        @JvmStatic
        fun from(connectionInfo: AppEnv.ConnectionInfo): RestClient =
            builder()
                .baseUrl(connectionInfo.host)
                .timeout(connectionInfo.timeout)
                .create()
                .build()

        /**
         * 간단한 URL로 RestClient를 생성합니다.
         */
        @JvmStatic
        fun simple(
            url: String,
            timeoutSeconds: Long = DEFAULT_TIMEOUT_SECONDS,
        ): RestClient =
            builder()
                .baseUrl(url)
                .timeout(timeoutSeconds)
                .create()
                .build()
    }
}
