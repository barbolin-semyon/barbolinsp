package ru.protei.barbolinsp.data.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.protei.barbolinsp.domain.KeySort
import ru.protei.barbolinsp.domain.Note
import ru.protei.barbolinsp.domain.NotesRemoteRepository
import javax.inject.Inject

class NotesGitHubRepository @Inject constructor(
    private val notesGitHubApi: NotesGitHubApi
) : NotesRemoteRepository {
    val ioDispatcher = Dispatchers.IO

    private fun toNote(issue: GitHubIssue): Note {
        return Note(
            id = issue.number,
            title = issue.title,
            text = issue.body
        )
    }

    private fun toIssue(note: Note): GitHubIssue {
        return GitHubIssue(
            number = note.id,
            title = note.title,
            body = note.text
        )
    }

    override suspend fun getAllNotes(keySort: KeySort): List<Note> = withContext(ioDispatcher) {
        val issues: List<GitHubIssue>?
        try {
            val result = notesGitHubApi.getList(keySort.value)

            if (!result.isSuccessful) {
                Log.w("NotesGithubRepository", "Can't get issues $result")
                return@withContext emptyList<Note>()
            }

            issues = result.body()
        } catch (e: Exception) {
            Log.w("NotesGithubRepository", "Can't get issues $e")
            return@withContext emptyList<Note>()
        }

        return@withContext issues?.map { toNote(it) } ?: emptyList()
    }

    override suspend fun insert(note: Note): Long? = withContext(ioDispatcher) {
        return@withContext try {
            val result = notesGitHubApi.add(toIssue(note))
            if (!result.isSuccessful) {
                Log.w("NotesGithubRepository", "Can't insert note $result")
                null
            } else {
                result.body()?.number
            }
        } catch (e: Exception) {
            Log.w("NotesGithubRepository", "Can't insert note $e")
            null
        }
    }

    override suspend fun update(note: Note): Boolean = withContext(ioDispatcher) {
        return@withContext try {
            val result = notesGitHubApi.update(note.id, toIssue(note))
            result.isSuccessful
        } catch (e: Exception) {
            Log.w("NotesGithubRepository", "Can't insert note $e")
            false
        }
    }

    override suspend fun deleteById(id: Long): Boolean = withContext(ioDispatcher) {
        return@withContext try {
            val result = notesGitHubApi.close(id)
            if (!result.isSuccessful) {
                Log.w("NotesGithubRepository", "Can't insert note $result")
                false
            } else {
                true
            }
        } catch (e: Exception) {
            Log.w("NotesGithubRepository", "Can't insert note $e")
            false
        }
    }
}