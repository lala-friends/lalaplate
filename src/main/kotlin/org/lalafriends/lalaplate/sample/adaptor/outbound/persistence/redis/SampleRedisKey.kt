package org.lalafriends.lalaplate.sample.adaptor.outbound.persistence.redis

import org.lalafriends.lalaplate.common.config.redis.RedisKey

class SampleRedisKey(
    private val keyName: String,
) : RedisKey {
    override fun getKey(): String = "$KEY:$keyName"

    companion object {
        const val KEY = "${RedisKey.PREFIX}:sample"
    }
}
