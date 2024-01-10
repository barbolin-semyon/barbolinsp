package ru.protei.barbolinsp.ui.notes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DetailNotesScreen(
    modifier: Modifier,
    title: String,
    text: String,
    onChangeTitle: (String) -> Unit,
    onChangeText: (String) -> Unit,
    clearSelectedNote: () -> Unit
) {
    BackHandler {
        clearSelectedNote()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onBackground,
                backgroundColor = Color(0x00FFFFFF)
            ),
            onValueChange = onChangeTitle,
            label = { Text(text = "Title") }
        )

        OutlinedTextField(
            value = text,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onBackground,
                backgroundColor = Color(0x00FFFFFF)
            ),
            onValueChange = onChangeText,
            modifier = Modifier
                .heightIn(60.dp)
                .fillMaxWidth(),
            label = { Text(text = "Description") }
        )
    }
}