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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.Room
import ru.protei.barbolinsp.data.NotesDatabase
import ru.protei.barbolinsp.data.NotesRepositoryDB
import ru.protei.barbolinsp.domain.NotesUseCase
import ru.protei.barbolinsp.ui.notes.NotesScreen
import ru.protei.barbolinsp.ui.notes.NotesViewModel
import ru.protei.barbolinsp.ui.theme.BarbolinspTheme

class MainActivity : ComponentActivity() {
    private val database: NotesDatabase by lazy {
        Room.databaseBuilder(
            this,
            NotesDatabase::class.java,
            "notes.db"
        ).fallbackToDestructiveMigration().build()
    }

    private val notesRepo by lazy { NotesRepositoryDB(database.notesDao()) }
    private val notesUseCase by lazy { NotesUseCase(notesRepo) }
    private val notesViewModelFactory by lazy {
        viewModelFactory {
            initializer {
                NotesViewModel(notesUseCase)
            }
        }
    }
    val viewModel: NotesViewModel by viewModels { notesViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isThemeDark by remember { mutableStateOf(false) }

            BarbolinspTheme(isThemeDark) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NotesScreen(viewModel) {
                        isThemeDark = isThemeDark.not()
                    }
                }
            }
        }
    }
}

