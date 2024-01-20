package ru.protei.barbolinsp.di

import android.content.Context
import androidx.room.Room
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.protei.barbolinsp.MainActivity
import ru.protei.barbolinsp.data.NotesDao
import ru.protei.barbolinsp.data.NotesDatabase
import ru.protei.barbolinsp.data.NotesRepositoryDB
import ru.protei.barbolinsp.domain.NotesUseCase
import javax.inject.Singleton

@Singleton
@Component (modules = [DatabaseLocalModule::class, DomainModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity) // for field inject property inside the MainActivity

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}

@Module
class DomainModule {
    @Provides
    @Singleton
    fun provideNotesUseCase(notesRepository: NotesRepositoryDB): NotesUseCase {
        return NotesUseCase(notesRepository)
    }
}

@Module
class DatabaseLocalModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context): NotesDatabase {
        return Room.databaseBuilder(
                context,
                NotesDatabase::class.java,
                "notes.db"
            ).fallbackToDestructiveMigration().build()
        }

    @Singleton
    @Provides
    fun provideDatabaseDao(notesDatabase: NotesDatabase) = notesDatabase.notesDao()

    @Singleton
    @Provides
    fun provideNotesRepositoryDB(notesDao: NotesDao): NotesRepositoryDB {
        return NotesRepositoryDB(notesDao)
    }
}