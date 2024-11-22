package org.lalafriends.lalaplate.sample

class Sample(
    val name: String,
) {
    var id: Any? = null
        private set

    fun setId(id: Any) {
        this.id = id
    }

    override fun toString(): String = "Sample(name=$name, id=$id)"
}
