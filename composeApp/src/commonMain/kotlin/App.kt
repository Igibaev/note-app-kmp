import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Calendar
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime


fun generateNumericId(): String {
    return Random.nextLong(Long.MAX_VALUE).toString()
}

@Composable
fun NotesApp() {
    var noteContent by remember { mutableStateOf("") }
    var noteCategory by remember { mutableStateOf("") }
    val categories = remember { mutableStateListOf<String>() } // Example categories
    var notes by remember { mutableStateOf<List<Note>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // Load initial notes from the database or API
        notes = getNotes()

        // Load initial categories from database or API
//        categories.addAll(getCategories())
    }

    MaterialTheme {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Notes", style = MaterialTheme.typography.h4)
            Spacer(modifier = Modifier.height(16.dp))

            NoteInput(
                noteContent = noteContent,
                onNoteContentChange = { noteContent = it },
                noteCategory = noteCategory,
                onNoteCategoryChange = { noteCategory = it },
                categories = categories,
                onAddNote = {
                    if (noteContent.isNotEmpty()) {
                        val newNote = Note(
                            id = generateNumericId(),
                            content = noteContent,
//                            category = noteCategory,
                        )
                        noteContent = ""
                        noteCategory = ""

                        // Save the new note to the database or send it to the API
                        coroutineScope.launch {
                            saveNote(newNote)
                            notes = getNotes() // Reload notes after adding a new one
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(notes, key = { it.id }) { note ->
                    NoteItem(note, onDelete = { noteToDelete ->
                        // Delete the note from the database or API
                        coroutineScope.launch {
                            deleteNote(noteToDelete.id)
                            notes = getNotes() // Reload notes after deleting one
                        }
                    }, onToggleDone = { noteToToggle ->
                        // Update the note in the database or API
                        coroutineScope.launch {
                            toggleNote(noteToToggle.id)
                            notes = getNotes() // Reload notes after deleting one
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun NoteInput(
    noteContent: String,
    onNoteContentChange: (String) -> Unit,
    noteCategory: String,
    onNoteCategoryChange: (String) -> Unit,
    categories: List<String>,
    onAddNote: () -> Unit
) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        val textStyle = TextStyle(fontSize = 18.sp)
        Box(
            modifier = Modifier
                .weight(1f)
                .background(Color.LightGray)
                .border(1.dp, Color.Gray, RoundedCornerShape(3.dp))
                .padding(8.dp)
        ) {
            BasicTextField(
                value = noteContent,
                onValueChange = onNoteContentChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = textStyle,
                decorationBox = { innerTextField ->
                    if (noteContent.isEmpty()) {
                        Text("Enter note...", color = Color.Gray, style = textStyle)
                    }
                    innerTextField()
                }
            )
        }
//        Spacer(modifier = Modifier.width(8.dp))
//        Box(modifier = Modifier
//            .weight(0.3f)
//            .background(Color.LightGray)
//            .border(1.dp, Color.Gray, RoundedCornerShape(3.dp))
//            .padding(8.dp)
//        ) {
//            DropdownMenu(selectedCategory = noteCategory, categories = categories, onCategorySelected = onNoteCategoryChange, textStyle = textStyle)
//        }

        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = onAddNote) {
            Text("Add")
        }
    }
}

@Composable
fun DropdownMenu(selectedCategory: String, categories: List<String>, onCategorySelected: (String) -> Unit, textStyle: TextStyle) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Column(modifier = Modifier.clickable(onClick = { expanded = true })) {
            Text(
                if (selectedCategory.isEmpty()) "Select Category" else selectedCategory,
                style = textStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.width(IntrinsicSize.Max)
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                categories.forEach { category ->
                    DropdownMenuItem(onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }) {
                        Text(category, style = textStyle)
                    }
                }
            }
        }
    }
}

@Composable
fun NoteItem(note: Note, onDelete: (Note) -> Unit, onToggleDone: (Note) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = note.isDone, onCheckedChange = { onToggleDone(note) })
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(note.content, style = MaterialTheme.typography.body1)
//                Text("Category: ${note.category}", style = MaterialTheme.typography.subtitle2)
                Text("Date: ${note.creationDate}", style = MaterialTheme.typography.subtitle2)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { onDelete(note) }) {
                Text("Delete")
            }
        }
    }
}

@Composable
@Preview
fun App() {
    NotesApp()
}

expect suspend fun deleteNote(id: String)
expect suspend fun toggleNote(id: String)
expect suspend fun saveNote(newNote: Note)
expect suspend fun getNotes(): List<Note>