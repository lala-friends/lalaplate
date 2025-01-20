package org.lalafriends.lalaplate.sample.adaptor.outbound.persistence.mongo

import jakarta.persistence.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

@Document
class SampleDocument(
    val name: String,
) {
    @Id
    var id: ObjectId = ObjectId.get()
}
