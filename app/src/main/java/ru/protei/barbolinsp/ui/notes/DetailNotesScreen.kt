package ru.protei.barbolinsp.ui.notes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.protei.barbolinsp.domain.Note

@Composable
fun DetailNotesScreen(modifier: Modifier, note: Note, clearSelectedNote: () -> Unit) {
    BackHandler {
        clearSelectedNote()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = note.title,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = note.text,
        )
    }
}