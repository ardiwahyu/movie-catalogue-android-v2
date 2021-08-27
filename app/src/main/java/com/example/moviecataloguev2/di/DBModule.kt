package com.example.moviecataloguev2.di

import android.content.Context
import androidx.room.Room
import com.example.moviecataloguev2.data.local.db.MovieCatalogueDB
import com.example.moviecataloguev2.data.local.db.dao.GenreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DBModule {
    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): MovieCatalogueDB {
        return Room
            .databaseBuilder(context, MovieCatalogueDB::class.java, "MovieCatalogue.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideGenreDao(db: MovieCatalogueDB): GenreDao {
        return db.genreDao()
    }
}