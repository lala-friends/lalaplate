package org.lalafriends.lalaplate.sample.persistence.jpa

import org.lalafriends.lalaplate.sample.Sample
import org.lalafriends.lalaplate.sample.service.SampleJdbcRepository
import org.springframework.data.jpa.repository.JpaRepository

interface SampleJpaRepository :
    SampleJdbcRepository,
    JpaRepository<Sample, Long>
