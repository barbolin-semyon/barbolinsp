package ru.protei.barbolinsp.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.protei.barbolinsp.data.local.NotesRepositoryDB
import javax.inject.Inject

class NotesUseCase @Inject constructor(
    private val notesRepositoryDB: NotesRepositoryDB
) {
    suspend fun fillWithInitialNotes(initialNotes: List<Note>) {
        notesRepositoryDB.deleteAll()
        for (note in initialNotes) {
            notesRepositoryDB.insert(note)
        }
    }

    suspend fun save(note: Note, actualNotes: List<Note>) {
        if (actualNotes.find { it.id == note.id } != null) {
            notesRepositoryDB.update(note)
        } else {
            notesRepositoryDB.insert(note)
        }
    }

    suspend fun notesFlow(keySort: KeySort = KeySort.ASC): Flow<List<Note>> = flow {
        notesRepositoryDB.getAllNotes(keySort)
    }

    suspend fun deleteById(id: Long) {
        notesRepositoryDB.deleteById(id)
    }

    suspend fun deleteAll() = notesRepositoryDB.deleteAll()
}