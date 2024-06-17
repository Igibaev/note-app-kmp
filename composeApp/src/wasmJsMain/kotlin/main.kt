import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    NoteApiProvider.noteApi = initApi()

    ComposeViewport(document.body!!) {
        App()
    }
}
