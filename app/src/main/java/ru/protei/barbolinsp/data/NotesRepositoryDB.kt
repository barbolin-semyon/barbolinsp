package ru.protei.barbolinsp.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.protei.barbolinsp.domain.Note
import ru.protei.barbolinsp.domain.NotesRepository

class NotesRepositoryDB (
    private val notesDao: NotesDao,
    override var notes: Flow<List<Note>>
) : NotesRepository {
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    override suspend fun getAllNotes(): Flow<List<Note>> = withContext(ioDispatcher) {
        return@withContext notesDao.getAll()
    }

    override suspend fun getAllNotesOfSortedASC(): Flow<List<Note>> = withContext(ioDispatcher) {
        return@withContext notesDao.getAllOfSortedASC()
    }
    override suspend fun insert(note: Note): Long =  withContext(ioDispatcher) {
        return@withContext notesDao.insert(note)
    }

    override suspend fun update(note: Note) =  withContext(ioDispatcher) {
        notesDao.update(note)
    }

    override suspend fun deleteById(id: Long) =  withContext(ioDispatcher) {
        notesDao.deleteById(id)
    }

    override suspend fun deleteAll() {
        notesDao.deleteAll()
    }
}