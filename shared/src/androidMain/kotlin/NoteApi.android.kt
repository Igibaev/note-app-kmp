import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

actual fun initApi(): NoteApi {
    val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json()
        }
        install(Logging) {
            level = LogLevel.ALL
        }
    }
    return DefaultNoteApi(client, "10.18.1.182", SERVER_PORT)
}

actual fun secondApi(): NoteApi {
    TODO("Not yet implemented")
}