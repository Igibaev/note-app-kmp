class NoteService(private val repository: NoteRepository) {
    suspend fun getNotes(): List<Note> {
        return repository.getNotes()
    }

    suspend fun getCategories(): List<String> {
        return repository.getCategories()
    }

    suspend fun addNote(note: Note) {
        repository.addNote(note)
    }

    suspend fun deleteNoteById(noteId: String) {
        repository.deleteNoteById(noteId)
    }

    suspend fun toggleNote(noteId: String) {
        val note = repository.getNoteById(noteId)
        if (note != null) {
            val updatedNote = note.copy(isDone = !note.isDone)
            repository.updateNote(updatedNote)
        }
    }
}