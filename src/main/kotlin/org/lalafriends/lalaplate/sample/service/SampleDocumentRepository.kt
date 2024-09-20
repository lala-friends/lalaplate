package org.lalafriends.lalaplate.sample.service

import org.lalafriends.lalaplate.sample.SampleDocument

interface SampleDocumentRepository {
    fun save(sampleDocument: SampleDocument): SampleDocument
}
