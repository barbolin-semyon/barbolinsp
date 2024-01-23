package ru.protei.barbolinsp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.protei.barbolinsp.domain.Note

@Database(
    entities = [Note::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}

@Database(
    entities = [Note::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class UndownloadedNotesDatabase : RoomDatabase() {
    abstract fun notesDao(): UndownloadedNotesDao
}