package org.lalafriends.lalaplate.sample.application.port.outbound

import org.lalafriends.lalaplate.sample.adaptor.outbound.persistence.mongo.SampleDocument

interface SampleDocumentRepository {
    fun save(sampleDocument: SampleDocument): SampleDocument
}
