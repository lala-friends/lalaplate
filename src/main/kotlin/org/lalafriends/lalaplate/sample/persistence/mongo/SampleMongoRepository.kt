package org.lalafriends.lalaplate.sample.persistence.mongo

import org.bson.types.ObjectId
import org.lalafriends.lalaplate.sample.persistence.SampleDocument
import org.lalafriends.lalaplate.sample.service.SampleDocumentRepository
import org.springframework.data.mongodb.repository.MongoRepository

interface SampleMongoRepository :
    SampleDocumentRepository,
    MongoRepository<SampleDocument, ObjectId>
