package ru.protei.barbolinsp.ui.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.protei.barbolinsp.domain.KeySort
import ru.protei.barbolinsp.domain.Note
import ru.protei.barbolinsp.domain.StateLoading

@Composable
fun NotesScreen(notesViewModel: NotesViewModel, changeTheme: () -> Unit) {
    val notesState by notesViewModel.notesState.collectAsState()

    val selectedNote by notesViewModel.selectedNote.collectAsState()
    var title by remember(selectedNote) { mutableStateOf(selectedNote?.title ?: "") }
    var text by remember(selectedNote) { mutableStateOf(selectedNote?.text ?: "") }

    var keySort by remember { mutableStateOf(KeySort.ASC) }
    LaunchedEffect(key1 = keySort, block = {
        notesViewModel.onChangeKeySort(keySort)
    })

    Scaffold(
        topBar = {
            NotesAppBar(
                keySort = keySort,
                onChangeTheme = changeTheme,
                onDeleteAllNotes = { notesViewModel.onDeleteAllNotes() },
                onChangeKeySort = { keySort = it }
            )
        },
        floatingActionButton = {
            NotesFub(isSelectedNote = selectedNote != null) {
                if (selectedNote != null) {
                    notesViewModel.onSaveNote(title, text)
                } else {
                    notesViewModel.onSelectNote()
                }
            }
        },
    ) {
        if (notesState is StateLoading.Loading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        if (notesState is StateLoading.Success<List<Note>>) {

            AnimatedVisibility(visible = selectedNote != null) {
                DetailNotesScreen(
                    modifier = Modifier.padding(it),
                    title = title,
                    text = text,
                    onChangeTitle = { title = it },
                    onChangeText = { text = it }
                ) {
                    notesViewModel.onClearSelectedNote()
                }
            }
            AnimatedVisibility(visible = selectedNote == null) {
                NotesListView(
                    modifier = Modifier.padding(it),
                    notes = (notesState as StateLoading.Success<List<Note>>).data,
                    onSelectNote = { notesViewModel.onSelectNote(it) },
                    onDeleteNote = { notesViewModel.onDeleteNoteById(it) }
                )
            }
        }

    }
}

@Composable
private fun NotesListView(
    modifier: Modifier,
    notes: List<Note> = listOf(),
    onSelectNote: (Note) -> Unit,
    onDeleteNote: (Long) -> Unit,
) {
    var idNoteOfLongClick by remember { mutableStateOf<Long>(0L) }

    if (idNoteOfLongClick != 0L) {
        AlertDialog(
            onDismissRequest = { idNoteOfLongClick = 0L },
            text = { Text(text = "Вы действительно хотите удалить заметку?") },
            buttons = {
                TextButton(onClick = { idNoteOfLongClick = 0L }) {
                    Text(text = "Отмена")
                }

                TextButton(onClick = {
                    onDeleteNote(idNoteOfLongClick)
                    idNoteOfLongClick = 0L
                }) {
                    Text(text = "Удалить", color = MaterialTheme.colors.error)
                }
            }
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.padding(horizontal = 8.dp),
    ) {
        items(notes) {
            NoteItemView(
                note = it,
                onClick = { onSelectNote(it) },
                onLongClick = {
                    idNoteOfLongClick = it.id
                })
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NoteItemView(
    note: Note,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    Card(
        Modifier.combinedClickable(
            onLongClick = onLongClick,
            onClick = onClick,
        ),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = note.title,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.h6
            )

            Text(
                text = note.text,
                maxLines = 1,
                color = MaterialTheme.colors.onSurface,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}