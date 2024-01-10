package ru.protei.barbolinsp.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.protei.barbolinsp.domain.Note
import ru.protei.barbolinsp.domain.NotesUseCase

class NotesViewModel(
    private val notesUseCase: NotesUseCase
) : ViewModel() {
    private val _selectedNote = MutableStateFlow<Note?>(null)
    val selectedNote: StateFlow<Note?>
        get() = _selectedNote

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>>
        get() = _notes

    init {
        viewModelScope.launch {
            notesUseCase.notesFlow()
                .collect {
                    _notes.value = it
                }
        }

        /*viewModelScope.launch {
            notesUseCase.fillWithInitialNotes(
                listOf(
                    Note(title = "Note 1", text = "Description 1"),
                    Note(title = "Note 2", text = "Description 2"),
                    Note(
                        title = "Note 3",
                        text = "Лол, с Гугл Драйва пропала тонна файлов — юзеры говорят, что утеряны все данные, загруженные после мая 2023 года.\n" +
                                "\n" +
                                "Чуваки с Гугл в шоке, но пока проводится расследование, всех пострадавших просят ничего не трогать у себя на Диске.\n" +
                                "\n" +
                                "Тут могла быть нативная реклама, но не сегодня."
                    ),
                    Note(title = "Note 4", text = "Description 4"),
                    Note(title = "Note 5", text = "Description 5"),
                )
            )
        }*/
    }

    fun onSelectNote(note: Note = Note(title = "", text = "")) {
        _selectedNote.value = note
    }

    fun onClearSelectedNote() {
        _selectedNote.value = null
    }

    fun onSaveNote(
        title: String = "",
        text: String = ""
    ) {
        if (title.isNotEmpty() && text.isNotEmpty()) {
            _selectedNote.update { note ->
                Note(
                    id = note!!.id,
                    title = title ?: note.title, // Мы уверены, что это не null
                    text = text ?: note.text
                )
            }
            viewModelScope.launch {
                notesUseCase.save(note = _selectedNote.value!!, actualNotes = _notes.value)
            }
        }
        _selectedNote.value = null
    }

    fun onRemoveNote(id: Long) {
        viewModelScope.launch {
            notesUseCase.deleteById(id)
        }
    }
}