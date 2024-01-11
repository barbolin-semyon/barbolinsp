package ru.protei.barbolinsp.ui.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.protei.barbolinsp.R
import ru.protei.barbolinsp.domain.KeySort

@Composable
fun NotesAppBar(
    keySort: KeySort,
    onChangeTheme: () -> Unit,
    onDeleteAllNotes: () -> Unit,
    onChangeKeySort: (KeySort) -> Unit,
) {
    TopAppBar(
        title = { Text("Skat.apps") },
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,

        actions = {
            Row(verticalAlignment = Alignment.CenterVertically) {

                IconButton(onClick = onDeleteAllNotes) {
                    Text(text = "Удалить всё")
                }

                IconButton(onClick = onChangeTheme) {
                    Icon(
                        modifier = Modifier.size(48.dp),
                        painter = painterResource(id = R.drawable.ic_change_theme),
                        contentDescription = "change theme",
                        tint = MaterialTheme.colors.onBackground,
                    )
                }

                var isExpandSortMenu by remember { mutableStateOf(false) }
                IconButton(onClick = { isExpandSortMenu = true }) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.ic_sort),
                        contentDescription = "Сортировка",
                        tint = MaterialTheme.colors.onBackground,
                    )

                    DropdownMenu(
                        expanded = isExpandSortMenu,
                        onDismissRequest = { isExpandSortMenu = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                onChangeKeySort(KeySort.ASC)
                                isExpandSortMenu = false
                            },
                            enabled = keySort != KeySort.ASC,
                        ) {
                            Text(text = "По возрастанию")
                        }

                        DropdownMenuItem(
                            onClick = {
                                onChangeKeySort(KeySort.DESC)
                                isExpandSortMenu = false
                            },
                            enabled = keySort != KeySort.DESC,
                        ) {
                            Text(text = "По убыванию")
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun NotesFub(isSelectedNote: Boolean, onClick: () -> Unit) {
    val agree by animateFloatAsState(
        targetValue = if (!isSelectedNote) 90f else 0f,
        animationSpec = tween(600),
        label = ""
    )

    FloatingActionButton(
        modifier = Modifier.rotate(agree),
        onClick = onClick
    ) {
        AnimatedVisibility(visible = !isSelectedNote) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "add note"
            )
        }

        AnimatedVisibility(visible = isSelectedNote) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = "add note"
            )
        }
    }
}