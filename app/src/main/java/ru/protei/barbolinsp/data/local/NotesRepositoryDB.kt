package ru.protei.barbolinsp.data.local

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.protei.barbolinsp.domain.Note
import ru.protei.barbolinsp.domain.NotesRepository
import javax.inject.Inject

class NotesRepositoryDB @Inject constructor(
    private val notesDao: NotesDao,
) : NotesRepository {
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    override suspend fun getAllNotesOfSortedAsc(): Flow<List<Note>> = withContext(ioDispatcher) {
        return@withContext notesDao.getAllOfSortedAsc()
    }

    override suspend fun getAllNotesOfSortedDesc(): Flow<List<Note>> = withContext(ioDispatcher) {
        return@withContext notesDao.getAllOfSortedDesc()
    }

    override suspend fun insert(note: Note): Long = withContext(ioDispatcher) {
        return@withContext notesDao.insert(note)
    }

    override suspend fun update(note: Note) = withContext(ioDispatcher) {
        notesDao.update(note)
    }

    override suspend fun deleteById(id: Long) = withContext(ioDispatcher) {
        notesDao.deleteById(id)
    }

    override suspend fun deleteAll() {
        notesDao.deleteAll()
    }
}