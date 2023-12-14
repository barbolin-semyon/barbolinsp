package ru.protei.barbolinsp.ui.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.protei.barbolinsp.R
import ru.protei.barbolinsp.domain.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(notesViewModel: NotesViewModel, changeTheme: () -> Unit) {
    val notes by notesViewModel.notes.collectAsState()
    val selectedNote by notesViewModel.selectedNote.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Skat.apps") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground,
                ),

                navigationIcon = {
                    AnimatedVisibility(visible = selectedNote != null) {
                        IconButton(onClick = { notesViewModel.clearSelectedNote() }) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                painter = painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = "change theme",
                                tint = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                    }
                },

                actions = {
                    IconButton(onClick = changeTheme) {
                        Icon(
                            modifier = Modifier.size(48.dp),
                            painter = painterResource(id = R.drawable.ic_change_theme),
                            contentDescription = "change theme",
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            val agree by animateFloatAsState(targetValue = if (selectedNote == null) 90f else 0f,
                animationSpec = tween(600),
                label = ""
            )

            FloatingActionButton(
                modifier = Modifier.rotate(agree),
                onClick = {
                }) {
                AnimatedVisibility(visible = selectedNote == null) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "add note"
                    )
                }

                AnimatedVisibility(visible = selectedNote != null) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "add note"
                    )
                }
            }
        },
    ) {
        AnimatedVisibility(visible = selectedNote != null) {
            DetailNotesScreen(
                modifier = Modifier.padding(it),
                note = selectedNote ?: Note(title = "", text = ""),
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