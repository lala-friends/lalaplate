package org.lalafriends.lalaplate.sample.persistence.redis

import org.lalafriends.lalaplate.config.redis.RedisKey

class SampleRedisKey(
    private val keyName: String,
) : RedisKey {
    override fun getKey(): String = "$KEY:$keyName"

    companion object {
        const val KEY = "${RedisKey.PREFIX}:sample"
    }
}
