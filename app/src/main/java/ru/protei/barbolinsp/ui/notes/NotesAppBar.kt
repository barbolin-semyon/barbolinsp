package ru.protei.barbolinsp.ui.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.protei.barbolinsp.R

@Composable
fun NotesAppBar(
    isSelectedNote: Boolean,
    changeTheme: () -> Unit,
    clearSelectedNote: () -> Unit,
) {
    TopAppBar(
        title = { Text("Skat.apps") },
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,


        navigationIcon = {
            AnimatedVisibility(visible = isSelectedNote) {
                IconButton(onClick = { clearSelectedNote() }) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "change theme",
                        tint = MaterialTheme.colors.onBackground,
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
                    tint = MaterialTheme.colors.onBackground,
                )
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