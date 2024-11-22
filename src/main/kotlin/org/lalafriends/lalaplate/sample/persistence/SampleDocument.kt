package org.lalafriends.lalaplate.sample.persistence

import jakarta.persistence.Id
import org.bson.types.ObjectId
import org.lalafriends.lalaplate.sample.Sample
import org.springframework.data.mongodb.core.mapping.Document

@Document
class SampleDocument(
    val name: String,
) {
    @Id
    var id: ObjectId = ObjectId.get()
        private set

    fun setId(id: ObjectId) {
        this.id = id
    }

    companion object {
        fun toDomain(sampleDocument: SampleDocument): Sample =
            Sample(
                name = sampleDocument.name,
            ).apply {
                setId(sampleDocument.id)
            }

        fun fromDomain(sample: Sample): SampleDocument =
            SampleDocument(sample.name).apply {
                setId(sample.id as ObjectId)
            }
    }
}
