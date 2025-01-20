package org.lalafriends.lalaplate.sample.adaptor.outbound.persistence.jpa

import org.lalafriends.lalaplate.sample.application.service.SampleJdbcRepository
import org.springframework.data.jpa.repository.JpaRepository

interface SampleJpaRepository :
    SampleJdbcRepository,
    JpaRepository<Sample, Long>
