actual suspend fun deleteNote(id: String) {
    val noteApi = NoteApiProvider.noteApi
    runCatching {
        noteApi.deleteNote(id);
    }
}

actual suspend fun toggleNote(id: String) {
    val noteApi = NoteApiProvider.noteApi
    runCatching {
        noteApi.toggleNote(id);
    }
}

actual suspend fun saveNote(newNote: Note) {
    val noteApi = NoteApiProvider.noteApi
    runCatching {
        noteApi.addNote(newNote);
    }
}

actual suspend fun getNotes(): List<Note> {
    val noteApi = NoteApiProvider.noteApi
    return runCatching {
        noteApi.getNotes();
    }.getOrDefault(emptyList())
}