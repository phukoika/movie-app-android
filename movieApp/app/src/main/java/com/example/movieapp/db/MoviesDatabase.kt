package com.example.movieapp.db

import android.content.Context
import androidx.room.*

@Database(entities = [com.example.movieapp.models.Result::class], version = 1)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun getMoviesDao(): MoviesDao

    companion object {
        @Volatile
        private var instance: MoviesDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }


    private fun createDatabase(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            MoviesDatabase::class.java,
            "movies_db"
        ).build()
    }
}