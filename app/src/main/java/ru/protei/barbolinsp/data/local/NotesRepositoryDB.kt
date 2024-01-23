package ru.protei.barbolinsp.data.local

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.protei.barbolinsp.domain.KeySort
import ru.protei.barbolinsp.domain.Note
import ru.protei.barbolinsp.domain.NotesLocalRepository
import javax.inject.Inject

class NotesRepositoryDB @Inject constructor(
    private val notesDao: NotesDao,
    private val undownloadedNotesDao: UndownloadedNotesDao
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

    override suspend fun getNoteById(id: Long): Note? = withContext(ioDispatcher) {
        return@withContext notesDao.getById(id)
    }

    override suspend fun getAllUndownloadedNotes(): List<Note> = withContext(ioDispatcher) {
        return@withContext undownloadedNotesDao.get()
    }

    override suspend fun insertUndownloadedNote(note: Note): Long = withContext(ioDispatcher){
        return@withContext undownloadedNotesDao.insert(note)
    }

    override suspend fun deleteUndownloadedById(id: Long): Boolean = withContext(ioDispatcher){
        return@withContext undownloadedNotesDao.deleteById(id) != null
    }
}