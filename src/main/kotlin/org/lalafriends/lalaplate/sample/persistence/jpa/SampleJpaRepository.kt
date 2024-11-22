package org.lalafriends.lalaplate.sample.persistence.jpa

import org.lalafriends.lalaplate.sample.persistence.SampleEntity
import org.lalafriends.lalaplate.sample.service.SampleJdbcRepository
import org.springframework.data.jpa.repository.JpaRepository

interface SampleJpaRepository :
    SampleJdbcRepository,
    JpaRepository<SampleEntity, Long>
