import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class DefaultNoteApi(private val client: HttpClient, host: String, port: Int): NoteApi {
    private val BASE_URL = "http://${host}:${port}/api/notes"

    override suspend fun getNotes(): List<Note> {
        return client.get(BASE_URL).body();
    }

    override suspend fun getCategories(): List<String> {
        return client.get("$BASE_URL/categories").body();
    }

    override suspend fun addNote(note: Note) {
        client.post("$BASE_URL") {
            contentType(ContentType.Application.Json)
            setBody(note)
        }
    }

    override suspend fun deleteNote(noteId: String) {
        client.delete("$BASE_URL") {
            url {
                parameters.append("noteId", noteId)
            }
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun toggleNote(noteId: String) {
        client.put("$BASE_URL/toggle") {
            url {
                parameters.append("noteId", noteId)
            }
            contentType(ContentType.Application.Json)
        }
    }
}