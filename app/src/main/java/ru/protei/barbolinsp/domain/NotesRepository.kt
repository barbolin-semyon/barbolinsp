package ru.protei.barbolinsp.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NotesRepository {
    suspend fun getAllNotesOfSortedAsc(): Flow<List<Note>>
    suspend fun getAllNotesOfSortedDesc(): Flow<List<Note>>
    suspend fun insert(note: Note): Long?
    suspend fun update(note: Note): Boolean
    suspend fun deleteById(id: Long): Boolean
    suspend fun deleteAll(): Boolean
}