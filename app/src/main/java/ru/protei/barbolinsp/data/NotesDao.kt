package ru.protei.barbolinsp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.protei.barbolinsp.domain.Note

@Dao
interface NotesDao {
    @Query("SELECT * FROM note")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY id ASC")
    fun getAllOfSortedASC(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

    @Query("DELETE FROM note WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM note")
    suspend fun deleteAll()
}