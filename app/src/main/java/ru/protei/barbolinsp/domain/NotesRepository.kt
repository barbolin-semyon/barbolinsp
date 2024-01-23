package ru.protei.barbolinsp.domain

import kotlinx.coroutines.flow.Flow

interface NotesRepository <T> {
    suspend fun getAllNotes(keySort: KeySort): T
    suspend fun insert(note: Note): Long?
    suspend fun update(note: Note): Boolean
    suspend fun deleteById(id: Long): Boolean

}

interface NotesLocalRepository : NotesRepository<Flow<List<Note>>> {
    suspend fun deleteAll(): Boolean
}
interface NotesRemoteRepository : NotesRepository<List<Note>>