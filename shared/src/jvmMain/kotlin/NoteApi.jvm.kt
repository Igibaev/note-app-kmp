actual fun initApi(): NoteApi {
    return JvmNoteApi(NoteService(InMemoryNoteRepository()))
}