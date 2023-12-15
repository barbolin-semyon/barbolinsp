package ru.protei.barbolinsp.ui.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.protei.barbolinsp.domain.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(notesViewModel: NotesViewModel, changeTheme: () -> Unit) {
    val notes by notesViewModel.notes.collectAsState()

    val selectedNote by notesViewModel.selectedNote.collectAsState()
    var title by remember(selectedNote) { mutableStateOf(selectedNote?.title ?: "") }
    var text by remember(selectedNote) { mutableStateOf(selectedNote?.text ?: "") }

    Scaffold(
        topBar = {
            NotesAppBar(selectedNote != null, changeTheme) {
                notesViewModel.clearSelectedNote()
            }
        },
        floatingActionButton = {
            NotesFub(isSelectedNote = selectedNote != null) {
                if (selectedNote != null) {
                    notesViewModel.editSelectedNote(title, text)
                } else {
                    notesViewModel.addSelectedNote("", "")
                }
            }
        },
    ) {
        AnimatedVisibility(visible = selectedNote != null) {
            DetailNotesScreen(
                modifier = Modifier.padding(it),
                title = title,
                text = text,
                onChangeTitle = { title = it },
                onChangeText = { text = it }
            ) {
                notesViewModel.clearSelectedNote()
            }
        }

        AnimatedVisibility(visible = selectedNote == null) {
            NotesListView(Modifier.padding(it), notes) {
                notesViewModel.setSelectedNote(it)
            }
        }
    }
}

@Composable
private fun NotesListView(
    modifier: Modifier,
    notes: List<Note> = listOf(),
    setSelectedNote: (Note) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.padding(horizontal = 8.dp),
    ) {
        items(notes) {
            NoteItemView(note = it, setSelectedNote = { setSelectedNote(it) })
        }
    }
}

@Composable
private fun NoteItemView(note: Note, setSelectedNote: () -> Unit = {}) {
    Card(
        Modifier.clickable { setSelectedNote() }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = note.text,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}