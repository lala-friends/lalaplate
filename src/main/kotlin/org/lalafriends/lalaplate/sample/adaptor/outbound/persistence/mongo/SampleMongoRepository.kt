package org.lalafriends.lalaplate.sample.adaptor.outbound.persistence.mongo

import org.bson.types.ObjectId
import org.lalafriends.lalaplate.sample.application.port.outbound.SampleDocumentRepository
import org.springframework.data.mongodb.repository.MongoRepository

interface SampleMongoRepository :
    SampleDocumentRepository,
    MongoRepository<SampleDocument, ObjectId>
