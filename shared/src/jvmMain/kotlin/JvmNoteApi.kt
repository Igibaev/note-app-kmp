class JvmNoteApi(private val noteService: NoteService): NoteApi {

    override suspend fun getNotes(): List<Note> {
        return noteService.getNotes();
    }

    override suspend fun addNote(note: Note) {
        noteService.addNote(note)
    }

    override suspend fun deleteNote(noteId: String) {
        noteService.deleteNoteById(noteId)
    }

    override suspend fun toggleNote(noteId: String) {
        noteService.toggleNote(noteId)
    }
}