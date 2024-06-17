interface NoteApi {
    suspend fun getNotes(): List<Note>
    suspend fun addNote(note: Note)
    suspend fun deleteNote(noteId: String)
    suspend fun toggleNote(noteId: String)
}

expect fun initApi(): NoteApi
