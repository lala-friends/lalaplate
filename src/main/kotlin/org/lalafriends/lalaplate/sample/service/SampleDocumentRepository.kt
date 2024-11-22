package org.lalafriends.lalaplate.sample.service

import org.lalafriends.lalaplate.sample.persistence.SampleDocument

interface SampleDocumentRepository {
    fun save(sampleDocument: SampleDocument): SampleDocument
}
