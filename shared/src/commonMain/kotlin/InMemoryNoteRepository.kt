class InMemoryNoteRepository: NoteRepository {
    private val notes = mutableListOf<Note>()
    private val categories = listOf("Personal", "Work", "Study")

    override suspend fun getNotes(): List<Note> {
        return notes.toList()
    }

    override suspend fun getNoteById(noteId: String): Note? {
        return notes.find { it.id == noteId }
    }

    override suspend fun addNote(note: Note) {
        notes.add(note)
    }

    override suspend fun deleteNoteById(noteId: String) {
        notes.removeAll { it.id == noteId }
    }

    override suspend fun updateNote(note: Note) {
        notes.find { it.id == note.id }?.let {
            notes[notes.indexOf(it)] = note
        }
    }

    override suspend fun getCategories(): List<String> {
        return categories.toList()
    }
}