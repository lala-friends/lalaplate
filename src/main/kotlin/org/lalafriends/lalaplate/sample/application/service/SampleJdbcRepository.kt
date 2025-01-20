package org.lalafriends.lalaplate.sample.application.service

import org.lalafriends.lalaplate.sample.adaptor.outbound.persistence.jpa.Sample

interface SampleJdbcRepository {
    fun save(sample: Sample): Sample
}
