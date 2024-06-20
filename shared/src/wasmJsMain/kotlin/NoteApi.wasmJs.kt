
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

actual fun initApi(): NoteApi {
    val client = HttpClient() {
        install(ContentNegotiation) {
            json()
        }
        defaultRequest {
            url {
                this.protocol = URLProtocol.HTTP
                this.host = "localhost"

            }
        }
    }
    return DefaultNoteApi(client, "localhost", SERVER_PORT)
}

actual fun secondApi(): NoteApi {
    TODO("Not yet implemented")
}