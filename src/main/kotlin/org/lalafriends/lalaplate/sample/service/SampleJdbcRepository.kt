package org.lalafriends.lalaplate.sample.service

import org.lalafriends.lalaplate.sample.Sample

interface SampleJdbcRepository {
    fun save(sample: Sample): Sample
}
