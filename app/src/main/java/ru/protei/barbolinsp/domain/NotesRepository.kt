package ru.protei.barbolinsp.domain

import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    val notes: Flow<List<Note>>
    suspend fun getAllNotes(): Flow<List<Note>>
    suspend fun getAllNotesOfSortedASC(): Flow<List<Note>>
    suspend fun insert(note: Note) : Long
    suspend fun update(note: Note)
    suspend fun deleteById(id: Long)
    suspend fun deleteAll()
}