package org.lalafriends.lalaplate.sample.service

import org.lalafriends.lalaplate.sample.persistence.SampleEntity

interface SampleJdbcRepository {
    fun save(sample: SampleEntity): SampleEntity
}
