package org.lalafriends.lalaplate.integration.sample

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.lalafriends.lalaplate.common.RoleHeader
import org.lalafriends.lalaplate.common.fromJson
import org.lalafriends.lalaplate.integration.config.FlowTestSupport
import org.lalafriends.lalaplate.sample.adaptor.inbound.SampleController
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class SampleControllerTest : FlowTestSupport() {
    private val userId = 1111L

    @Test
    @DisplayName("Sample Controller 테스트")
    fun hello() {
        val uri = linkTo<SampleController> { hello() }.toUri()
        val result =
            mockMvcFlow(
                HttpMethod.GET,
                uri,
                listOf(
                    Pair(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON),
                    Pair(RoleHeader.User.KEY, userId),
                ),
            )
        assertThat(result).isEqualTo("Hello, world!")
    }

    @Test
    @DisplayName("Sample Controller 테스트 - redis")
    fun redis() {
        val uri = linkTo<SampleController> { helloRedis() }.toUri()
        val result =
            mockMvcFlow(
                HttpMethod.GET,
                uri,
                listOf(
                    Pair(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON),
                    Pair(RoleHeader.User.KEY, userId),
                ),
            )
        assertThat(result).isEqualTo("hello[1]")
    }

    @Test
    @DisplayName("Sample Controller 테스트 - mongo")
    fun mongo() {
        val uri = linkTo<SampleController> { helloMongo() }.toUri()
        val result =
            mockMvcFlow(
                HttpMethod.GET,
                uri,
                listOf(
                    Pair(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON),
                    Pair(RoleHeader.User.KEY, userId),
                ),
            )
        assertThat(result).isNotBlank()
    }

    @Test
    @DisplayName("Sample Controller 테스트 - jpa")
    fun jpa() {
        val uri = linkTo<SampleController> { helloJpa() }.toUri()
        val result =
            mockMvcFlow(
                HttpMethod.GET,
                uri,
                listOf(
                    Pair(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON),
                    Pair(RoleHeader.User.KEY, userId),
                ),
            )
        assertThat(result).isNotBlank()
    }

    @Test
    @DisplayName("Sample Controller 테스트 - error")
    fun error() {
        val uri = linkTo<SampleController> { helloError() }.toUri()
        val result =
            mockMvcFlow(
                HttpMethod.GET,
                uri,
                listOf(
                    Pair(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON),
                    Pair(RoleHeader.User.KEY, userId),
                ),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR,
            ).fromJson<Map<String, String>>()
        assertAll(
            { assertThat(result["message"]).isEqualTo("runtime exception") },
            { assertThat(result["path"]).isEqualTo("/v1/hello-error") },
            { assertThat(result["type"]).isEqualTo("RuntimeException") },
        )
    }

    @Test
    @DisplayName("Sample Controller 테스트 - ExRate client")
    fun exRate() {
        val uri = linkTo<SampleController> { helloExRate() }.toUri()
        val result =
            mockMvcFlow(
                HttpMethod.GET,
                uri,
                listOf(
                    Pair(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON),
                    Pair(RoleHeader.User.KEY, userId),
                ),
            )
        assertThat(result).isNotBlank()
    }
}
