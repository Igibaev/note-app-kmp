import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: String,
    val content: String,
//    val category: String,
    val isDone: Boolean = false,
    var creationDate: String = ""
)