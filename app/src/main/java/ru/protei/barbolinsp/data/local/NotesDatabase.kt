package ru.protei.barbolinsp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.protei.barbolinsp.domain.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)

abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}