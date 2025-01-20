package org.lalafriends.lalaplate.common.config.redis

interface RedisKey {
    fun getKey(): String

    companion object {
        const val PREFIX = "lalaplate:"
    }
}
