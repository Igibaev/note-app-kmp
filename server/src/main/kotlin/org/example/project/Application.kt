package org.example.project


import InMemoryNoteRepository
import Note
import NoteService
import SERVER_PORT
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun main() {
    embeddedServer(Netty, port = SERVER_PORT) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                encodeDefaults = true
            })
        }
        install(CORS) {
            allowMethod(HttpMethod.Options)
            allowMethod(HttpMethod.Put)
            allowMethod(HttpMethod.Delete)
            allowMethod(HttpMethod.Patch)
            allowHeader(HttpHeaders.Authorization)
            allowHeader(HttpHeaders.AccessControlAllowOrigin)
            allowNonSimpleContentTypes = true
            allowCredentials = true
            allowSameOrigin = true
            anyHost()
        }
        routing {
            noteRoutes()
        }
    }.start(wait = true)
}

fun Route.noteRoutes() {
    val noteService = NoteService(InMemoryNoteRepository())

    route("/api/notes") {
        get {
            call.respond(noteService.getNotes())
        }

        get("/categories") {
            call.respond(noteService.getCategories())
        }

        post {
            val note = call.receive<Note>()
            note.apply {
                creationDate = getCurrentDateTime()
            }
            noteService.addNote(note)
            call.respond(note)
        }

        put("/toggle") {
            val noteId = call.parameters["noteId"]
            if (noteId != null) {
                noteService.toggleNote(noteId)
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Missing or malformed noteId")
            }
        }

        delete {
            val noteId = call.parameters["noteId"]
            if (noteId != null) {
                noteService.deleteNoteById(noteId)
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Missing or malformed noteId")
            }
        }
    }
}

fun getCurrentDateTime(): String {
    val current = LocalDateTime.now()

    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
    return current.format(formatter)
}
