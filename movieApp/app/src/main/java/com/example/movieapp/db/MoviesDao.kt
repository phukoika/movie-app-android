package com.example.movieapp.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(result: com.example.movieapp.models.Result) : Long

    @Query("Select * from Movie")
    fun getAllMovies(): LiveData<List<com.example.movieapp.models.Result>>

    @Delete
    suspend fun deleteMovie(result: com.example.movieapp.models.Result)


}