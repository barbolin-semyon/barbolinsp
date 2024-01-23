package ru.protei.barbolinsp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.protei.barbolinsp.domain.Note

@Dao
interface UndownloadedNotesDao {
    @Query("SELECT * FROM note")
    fun get(): List<Note>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note): Long

    @Query("DELETE FROM note WHERE id = :id")
    suspend fun deleteById(id: Long): Int?
}