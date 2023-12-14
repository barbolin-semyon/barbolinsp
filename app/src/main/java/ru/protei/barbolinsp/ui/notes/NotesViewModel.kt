package ru.protei.barbolinsp.ui.notes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.protei.barbolinsp.domain.Note

class NotesViewModel : ViewModel() {
    private val _selectedNote = MutableStateFlow<Note?>(null)
    val selectedNote: StateFlow<Note?>
        get() = _selectedNote

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>>
        get() = _notes

    fun isSelectedNote() = _selectedNote.value != null

    fun setSelectedNote(note: Note) {
        _selectedNote.value = note
    }

    fun clearSelectedNote() {
        _selectedNote.value = null
    }

    fun editSelectedNote(
        title: String? = null,
        text: String? = null
    ) {
        _selectedNote.update { note ->
            Note(
                title = title ?: note!!.title, // Мы уверены, что это не null
                text = text ?: note!!.text
            )
        }

        val temp = _notes.value.toMutableList()
        val index = temp.indexOfFirst { it.id == selectedNote.value!!.id }
        temp[index] = selectedNote.value!!
        _notes.value = temp
    }

    fun addSelectedNote(title: String, text: String) {
        val newNote = Note(title = title, text = text)
        val temp = _notes.value.toMutableList()
        temp.add(newNote)
        _notes.value = temp
        _selectedNote.value = newNote
    }
    fun removeNote(id: String) {
        _notes.value = _notes.value.filter { it.id != id }
    }
}