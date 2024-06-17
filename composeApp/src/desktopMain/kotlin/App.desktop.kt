val noteService = NoteService(InMemoryNoteRepository())

actual suspend fun deleteNote(id: String) {
    noteService.deleteNoteById(id)
}

actual suspend fun toggleNote(id: String) {
    noteService.toggleNote(id)
}

actual suspend fun saveNote(newNote: Note) {
    newNote.apply {
        creationDate = "undefined"
    }
    noteService.addNote(newNote)
}

actual suspend fun getNotes(): List<Note> {
    return noteService.getNotes()
}
