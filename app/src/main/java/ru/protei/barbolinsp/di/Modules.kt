package ru.protei.barbolinsp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.protei.barbolinsp.data.local.NotesDao
import ru.protei.barbolinsp.data.local.NotesDatabase
import ru.protei.barbolinsp.data.local.UndownloadedNotesDao
import ru.protei.barbolinsp.data.local.UndownloadedNotesDatabase
import ru.protei.barbolinsp.data.remote.NotesGitHubApi
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
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideDao(notesDatabase: NotesDatabase): NotesDao {
        return notesDatabase.notesDao()
    }

    @Singleton
    @Provides
    fun provideUndownloadedNotesDatabase(@ApplicationContext context: Context): UndownloadedNotesDatabase {
        return Room.databaseBuilder(
            context,
            UndownloadedNotesDatabase::class.java,
            "undownloaded_notes.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideUndownloadedNotesDao(notesDatabase: UndownloadedNotesDatabase): UndownloadedNotesDao {
        return notesDatabase.notesDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
class DatabaseRemoteModule {
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer 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"
                    ).build()
                chain.proceed(request)
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/repos/barbolin-semyon/barbolinsp/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideNotesGitHubApi(retrofit: Retrofit): NotesGitHubApi =
        retrofit.create(NotesGitHubApi::class.java)
}
