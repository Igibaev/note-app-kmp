actual fun initApi(): NoteApi {
    return JvmNoteApi(NoteService(InMemoryNoteRepository()))
}

actual fun secondApi(): NoteApi {
    TODO("Not yet implemented")
}