package org.lalafriends.lalaplate.sample.application.port.outbound

import org.lalafriends.lalaplate.sample.adaptor.outbound.persistence.jpa.SampleEntity

interface SampleJdbcRepository {
    fun save(sample: SampleEntity): SampleEntity
}
