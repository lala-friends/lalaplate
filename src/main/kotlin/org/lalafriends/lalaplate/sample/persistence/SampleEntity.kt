package org.lalafriends.lalaplate.sample.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.lalafriends.lalaplate.sample.Sample

@Entity
class SampleEntity(
    @Column(nullable = false, columnDefinition = "VARCHAR(500)")
    var name: String = "",
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        private set

    fun setId(id: Long) {
        this.id = id
    }

    companion object {
        fun toDomain(sampleEntity: SampleEntity): Sample =
            Sample(sampleEntity.name).apply {
                sampleEntity.id?.let { setId(it) }
            }

        fun fromDomain(sample: Sample): SampleEntity =
            SampleEntity(sample.name).apply {
                sample.id?.let { setId(it as Long) }
            }
    }
}
