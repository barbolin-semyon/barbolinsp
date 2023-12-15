package ru.protei.barbolinsp.ui.notes

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.protei.barbolinsp.domain.Note

class NotesViewModel : ViewModel() {
    private val _selectedNote = MutableStateFlow<Note?>(null)
    val selectedNote: StateFlow<Note?>
        get() = _selectedNote

    private val _notes = MutableStateFlow<List<Note>>(
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
    val notes: StateFlow<List<Note>>
        get() = _notes

    fun setSelectedNote(note: Note) {
        _selectedNote.value = note
    }

    fun clearSelectedNote() {
        _selectedNote.value = null
    }

    fun editSelectedNote(
        title: String = "",
        text: String = ""
    ) {
        if (title.isEmpty() && text.isEmpty()) {
            removeNote(_selectedNote.value!!.id)
        } else {
            _selectedNote.update { note ->
                Note(
                    id = note!!.id,
                    title = title ?: note.title, // Мы уверены, что это не null
                    text = text ?: note.text
                )
            }

            val temp = _notes.value.toMutableList()
            val index = temp.indexOfFirst { it.id == selectedNote.value!!.id }
            Log.i("QWE", "index = $index temp = ${temp.size}")
            temp[index] = selectedNote.value!!
            _notes.value = temp
        }
        _selectedNote.value = null
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