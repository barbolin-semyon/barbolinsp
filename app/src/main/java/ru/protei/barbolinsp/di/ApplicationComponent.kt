package ru.protei.barbolinsp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.DefineComponent
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.protei.barbolinsp.data.NotesDao
import ru.protei.barbolinsp.data.NotesDatabase
import ru.protei.barbolinsp.data.NotesRepositoryDB
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseLocalModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NotesDatabase {
        return Room.databaseBuilder(
                context,
                NotesDatabase::class.java,
                "notes.db"
            ).build()
        }

    @Singleton
    @Provides
    fun provideDao(notesDatabase: NotesDatabase): NotesDao {
        return notesDatabase.notesDao()
    }
}
