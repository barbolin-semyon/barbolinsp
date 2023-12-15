package ru.protei.barbolinsp.ui.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.protei.barbolinsp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesAppBar(
    isSelectedNote: Boolean,
    changeTheme: () -> Unit,
    clearSelectedNote: () -> Unit,
) {
    TopAppBar(
        title = { Text("Skat.apps") },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground,
        ),

        navigationIcon = {
            AnimatedVisibility(visible = isSelectedNote) {
                IconButton(onClick = { clearSelectedNote() }) {
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