package ru.protei.barbolinsp.domain

import kotlinx.coroutines.flow.Flow

class NotesUseCase(private val notesRepository: NotesRepository) {
    suspend fun fillWithInitialNotes(initialNotes: List<Note>) {
        notesRepository.deleteAll()
        for (note in initialNotes) {
            notesRepository.insert(note)
        }
    }

    suspend fun save(note: Note, actualNotes: List<Note>) {
        if (actualNotes.find { it.id == note.id } != null) {
            notesRepository.update(note)
        } else {
            notesRepository.insert(note)
        }
    }

    suspend fun notesFlow(keySort: KeySort = KeySort.ASC): Flow<List<Note>> {
        return if (keySort  == KeySort.DESC) {
            notesRepository.getAllNotesOfSortedDesc()
        } else {
            notesRepository.getAllNotesOfSortedAsc()
        }
    }

    suspend fun deleteById(id: Long) {
        notesRepository.deleteById(id)
    }

    suspend fun deleteAll() = notesRepository.deleteAll()
}