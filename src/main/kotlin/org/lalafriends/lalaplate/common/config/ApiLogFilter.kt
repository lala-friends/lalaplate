package org.lalafriends.lalaplate.common.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.lalafriends.lalaplate.common.Jackson
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.IOException
import java.nio.charset.StandardCharsets

@Component
class ApiLogFilter : OncePerRequestFilter() {
    private val objectMapper = Jackson.mapper()
    private val maxPayloadLength = 10000

    private val log = LoggerFactory.getLogger(ApiLogFilter::class.java)

    // 로깅에 필요한 헤더만 포함
    private val allowedHeaders = listOf("Authorization", "Content-Type", "Accept", "User-Agent")

    // 로깅에서 제외할 경로
    private val excludePaths =
        listOf("/actuator/health", "/favicon.ico", "/static/**", "/actuator/**", "/h2-console/**")

    override fun shouldNotFilter(request: HttpServletRequest): Boolean =
        excludePaths.any { path ->
            AntPathMatcher().match(path, request.requestURI)
        }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val wrappedRequest = ContentCachingRequestWrapper(request)
        val wrappedResponse = ContentCachingResponseWrapper(response)
        val startTime = System.currentTimeMillis()
        try {
            logRequest(wrappedRequest)
            filterChain.doFilter(wrappedRequest, wrappedResponse)
        } catch (e: Exception) {
            log.error("Exception occurred during request processing: ${e.message}", e)
            throw e
        } finally {
            val duration = System.currentTimeMillis() - startTime
            if (!isMultipartRequest(request)) {
                logResponse(wrappedResponse, duration)
            } else {
                log.info("Multipart request - Skipping body logging")
            }
            wrappedResponse.copyBodyToResponse()
        }
    }

    private fun isMultipartRequest(request: HttpServletRequest): Boolean {
        val contentType = request.contentType
        return contentType != null && contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)
    }

    private fun logRequest(request: ContentCachingRequestWrapper) {
        val requestBody = readRequestBody(request)
        val maskedRequestBody = PrivacyMasking.mask(requestBody, maxPayloadLength)
        val filteredHeaders = filterHeaders(request)
        val logMap =
            mapOf(
                "type" to "REQ",
                "method" to request.method,
                "uri" to request.requestURI,
                "query" to (request.queryString ?: ""),
                "headers" to filteredHeaders,
                "body" to maskedRequestBody,
            )
        val logJson = objectMapper.writeValueAsString(logMap)
        log.info(logJson)
    }

    private fun logResponse(
        response: ContentCachingResponseWrapper,
        duration: Long,
    ) {
        val responseBody = readResponseBody(response)
        val maskedResponseBody = PrivacyMasking.mask(responseBody, maxPayloadLength)
        val logMap =
            mapOf(
                "type" to "RES",
                "status" to response.status,
                "duration" to duration,
                "headers" to response.headerNames.associateWith { response.getHeader(it) },
                "body" to maskedResponseBody,
            )
        val logJson = objectMapper.writeValueAsString(logMap)

        // 응답 상태 코드에 따라 로그 레벨 분리
        when {
            response.status >= 500 -> log.error(logJson)
            response.status >= 400 -> log.warn(logJson)
            else -> log.info(logJson)
        }
    }

    private fun filterHeaders(request: HttpServletRequest): Map<String, String> =
        request.headerNames
            .toList()
            .filter { allowedHeaders.contains(it) }
            .associateWith { request.getHeader(it) }

    private fun readRequestBody(request: ContentCachingRequestWrapper): String =
        try {
            val content = request.contentAsByteArray
            if (content.isNotEmpty()) {
                String(content, StandardCharsets.UTF_8)
            } else {
                "[empty body]"
            }
        } catch (e: IOException) {
            log.warn("Error reading request body", e)
            "[error reading request body]"
        }

    private fun readResponseBody(response: ContentCachingResponseWrapper): String =
        try {
            val content = response.contentAsByteArray
            if (content.isNotEmpty()) {
                String(content, StandardCharsets.UTF_8)
            } else {
                "[empty body]"
            }
        } catch (e: IOException) {
            log.warn("Error reading response body", e)
            "[error reading response body]"
        }
}

object PrivacyMasking {
    private val sensitivePatterns =
        mapOf(
            "phone" to Regex("\"phone\"\\s*:\\s*\"([^\"]+)\""),
            "email" to Regex("\"email\"\\s*:\\s*\"([^\"]+)\""),
            "address" to Regex("\"address\"\\s*:\\s*\"([^\"]+)\""),
            "password" to Regex("\"password\"\\s*:\\s*\"([^\"]+)\""),
            "ssn" to Regex("\\d{6}-?\\d{7}"), // 주민등록번호 패턴
            "card" to Regex("\\d{4}-?\\d{4}-?\\d{4}-?\\d{4}"), // 신용카드 번호 패턴
        )

    fun mask(
        input: String,
        maxLength: Int,
    ): String {
        var result = input

        // 최대 길이 제한
        if (result.length > maxLength) {
            result = result.substring(0, maxLength) + "... [truncated]"
        }

        // 민감 정보 마스킹
        sensitivePatterns.forEach { (field, pattern) ->
            if (field in listOf("phone", "email", "address", "password")) {
                // JSON 필드 기반 마스킹
                result =
                    pattern.replace(result) { matchResult ->
                        val group1 = matchResult.groups[1]
                        if (group1 != null) {
                            val range = group1.range
                            matchResult.value.replaceRange(range, "*".repeat(range.last - range.first + 1))
                        } else {
                            matchResult.value
                        }
                    }
            } else {
                // 패턴 기반 마스킹 (SSN, 카드번호 등)
                result =
                    pattern.replace(result) { matchResult ->
                        matchResult.value.replace(Regex("\\d"), "*")
                    }
            }
        }

        return result
    }
}
