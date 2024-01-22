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


}
