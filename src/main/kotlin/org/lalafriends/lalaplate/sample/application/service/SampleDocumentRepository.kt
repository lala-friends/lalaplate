package org.lalafriends.lalaplate.sample.application.service

import org.lalafriends.lalaplate.sample.adaptor.outbound.persistence.mongo.SampleDocument

interface SampleDocumentRepository {
    fun save(sampleDocument: SampleDocument): SampleDocument
}
