package org.lalafriends.lalaplate.config.redis

interface RedisKey {
    fun getKey(): String

    companion object {
        const val PREFIX = "lalaplate:"
    }
}
