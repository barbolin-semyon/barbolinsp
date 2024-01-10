package ru.protei.barbolinsp.domain

import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun getAllNotesOfSortedAsc(): Flow<List<Note>>
    suspend fun getAllNotesOfSortedDesc(): Flow<List<Note>>
    suspend fun insert(note: Note) : Long
    suspend fun update(note: Note)
    suspend fun deleteById(id: Long)
    suspend fun deleteAll()
}