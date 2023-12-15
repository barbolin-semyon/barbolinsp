package ru.protei.barbolinsp.ui.notes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
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
                textColor = MaterialTheme.colorScheme.onBackground,
                containerColor = Color(0x00FFFFFF)
            ),
            onValueChange = onChangeTitle,
            label = { Text(text = "Title") }
        )

        OutlinedTextField(
            value = text,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onBackground,
                containerColor = Color(0x00FFFFFF)
            ),
            onValueChange = onChangeText,
            modifier = Modifier
                .heightIn(60.dp)
                .fillMaxWidth(),
            label = { Text(text = "Description") }
        )
    }
}