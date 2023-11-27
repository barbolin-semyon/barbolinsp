package ru.protei.barbolinsp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.protei.barbolinsp.domain.Note
import ru.protei.barbolinsp.ui.theme.BarbolinspTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarbolinspTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val notes = remember {
                        listOf(
                            Note("Note 1", "Description 1"),
                            Note("Note 2", "Description 2"),
                            Note(
                                "Note 3",
                                "Лол, с Гугл Драйва пропала тонна файлов — юзеры говорят, что утеряны все данные, загруженные после мая 2023 года.\n" +
                                        "\n" +
                                        "Чуваки с Гугл в шоке, но пока проводится расследование, всех пострадавших просят ничего не трогать у себя на Диске.\n" +
                                        "\n" +
                                        "Тут могла быть нативная реклама, но не сегодня."
                            ),
                            Note("Note 4", "Description 4"),
                            Note("Note 5", "Description 5"),
                        )
                    }

                    NotesView(notes)
                }
            }
        }
    }
}

@Preview
@Composable
private fun NotesView(notes: List<Note> = listOf()) {
    LazyColumn(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(notes) {
            NoteItemView(it)
        }
    }
}

@Composable
private fun NoteItemView(note: Note) {
    var isExpand by remember { mutableStateOf(false) }
    Card(
        Modifier
            .fillMaxWidth()
            .clickable { isExpand = !isExpand }) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleMedium
                )

                AnimatedVisibility(isExpand) {
                    Text(
                        text = note.text,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Icon(
                painter = painterResource(
                    id =
                    if (isExpand) R.drawable.baseline_arrow_drop_up_24
                    else R.drawable.baseline_arrow_drop_down_24
                ),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null,
            )
        }
    }
}