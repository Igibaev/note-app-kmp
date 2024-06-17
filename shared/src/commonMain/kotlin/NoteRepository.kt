interface NoteRepository {
    suspend fun getNotes(): List<Note>
    suspend fun getNoteById(noteId: String): Note?
    suspend fun addNote(note: Note)
    suspend fun deleteNoteById(noteId: String)
    suspend fun updateNote(note: Note)
}