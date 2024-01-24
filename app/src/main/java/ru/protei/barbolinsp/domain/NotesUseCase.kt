package ru.protei.barbolinsp.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.protei.barbolinsp.data.local.NotesRepositoryDB
import ru.protei.barbolinsp.data.remote.NotesGitHubRepository
import javax.inject.Inject

class NotesUseCase @Inject constructor(
    private val notesRepositoryDB: NotesRepositoryDB,
    private val notesGitHubRepository: NotesGitHubRepository
) {
    suspend fun save(note: Note, actualNotes: List<Note>) {
        if (actualNotes.find { it.id == note.id } != null) {
            notesGitHubRepository.update(note)
            notesRepositoryDB.update(note)
        } else {
            val result = notesGitHubRepository.insert(note)
            if (result != null) {
                notesRepositoryDB.deleteById(note.id)
                notesRepositoryDB.deleteUndownloadedById(note.id)
                notesRepositoryDB.insert(Note(result, note.title, note.text, note.updateAt))
            } else {
                notesRepositoryDB.insert(note)
                notesRepositoryDB.insertUndownloadedNote(note)
            }
        }
    }

    suspend fun loadNotes(keySort: KeySort = KeySort.ASC): Flow<StateLoading<List<Note>>> = flow {
        emit(StateLoading.Loading)
        val remoteNotes = notesGitHubRepository.getAllNotes(keySort)
        val localUndownloadedNotes = notesRepositoryDB.getAllUndownloadedNotes()

        localUndownloadedNotes.forEach {
            save(it, remoteNotes)
        }

        remoteNotes.forEach { currentRemoteNote ->
            saveRemoteNoteToDB (
                remoteNote = currentRemoteNote,
                localNote = notesRepositoryDB.getNoteById(currentRemoteNote.id)
            )
        }

        notesRepositoryDB.getAllNotes(keySort).collect {
            emit(StateLoading.Success(it))
        }
    }

    private suspend fun saveRemoteNoteToDB(remoteNote: Note, localNote: Note?) {
        if (localNote == null) {
            notesRepositoryDB.insert(remoteNote)
        } else if (localNote.updateAt != remoteNote.updateAt) {
            notesRepositoryDB.update(remoteNote)
        }
    }

    suspend fun deleteById(id: Long) {
        notesGitHubRepository.deleteById(id)
        notesRepositoryDB.deleteById(id)
    }

    suspend fun deleteAll(notes: List<Note>) {
        notes.forEach {
            notesGitHubRepository.deleteById(it.id)
        }
        notesRepositoryDB.deleteAll()
    }
}

