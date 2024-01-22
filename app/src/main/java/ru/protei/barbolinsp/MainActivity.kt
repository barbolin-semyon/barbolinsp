package ru.protei.barbolinsp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import ru.protei.barbolinsp.ui.notes.NotesScreen
import ru.protei.barbolinsp.ui.notes.NotesViewModel
import ru.protei.barbolinsp.ui.theme.BarbolinspTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isThemeDark by remember { mutableStateOf(false) }

            BarbolinspTheme(isThemeDark) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel: NotesViewModel by viewModels()
                    NotesScreen(viewModel) {
                        isThemeDark = isThemeDark.not()
                    }
                }
            }
        }
    }
}


