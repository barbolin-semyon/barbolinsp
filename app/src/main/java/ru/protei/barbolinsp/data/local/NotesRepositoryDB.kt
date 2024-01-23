package ru.protei.barbolinsp.data.local

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.protei.barbolinsp.domain.KeySort
import ru.protei.barbolinsp.domain.Note
import ru.protei.barbolinsp.domain.NotesLocalRepository
import ru.protei.barbolinsp.domain.NotesRepository
import javax.inject.Inject

class NotesRepositoryDB @Inject constructor(
    private val notesDao: NotesDao,
) : NotesLocalRepository {
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun getAllNotes(keySort: KeySort): Flow<List<Note>> {
        return when (keySort) {
            KeySort.ASC -> notesDao.getAllOfSortedAsc()
            KeySort.DESC -> notesDao.getAllOfSortedDesc()
        }
    }

    override suspend fun insert(note: Note): Long = withContext(ioDispatcher) {
        return@withContext notesDao.insert(note)
    }

    override suspend fun update(note: Note): Boolean = withContext(ioDispatcher) {
        return@withContext notesDao.update(note) != null
    }

    override suspend fun deleteById(id: Long): Boolean = withContext(ioDispatcher) {
        return@withContext notesDao.deleteById(id) != null
    }

    override suspend fun deleteAll(): Boolean = withContext(ioDispatcher) {
        return@withContext notesDao.deleteAll() != null
    }
}