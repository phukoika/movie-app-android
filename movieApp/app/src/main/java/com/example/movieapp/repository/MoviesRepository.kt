package com.example.movieapp.repository

import com.example.movieapp.api.RetrifitInstance
import com.example.movieapp.db.MoviesDatabase

class MoviesRepository(val db: MoviesDatabase) {
    suspend fun getPopularMovies(pageNumber : Int) = RetrifitInstance.api.getPopularMovies(pageNumber)

    suspend fun getTopRated(pageNumber: Int) = RetrifitInstance.api.getTopRated(pageNumber)

    suspend fun getActors(movies_id: String) = RetrifitInstance.api.getActors(movies_id)

    suspend fun getSimilarMovie(movies_id: String) = RetrifitInstance.api.getSimilarMovie(movies_id)

    suspend fun getSeriesMovies(movies_id: Int) = RetrifitInstance.api.getSeries(movies_id)

    suspend fun getReviews(movies_id: String) = RetrifitInstance.api.getReviews(movies_id)

    suspend fun getSearching(pageNumber : Int,query: String) = RetrifitInstance.api.getSearchMovie(pageNumber, query)


    suspend fun getVideo(movies_id: String) = RetrifitInstance.api.getVideo(movies_id)
    // room database
    suspend fun insertMovie(result: com.example.movieapp.models.Result) = db.getMoviesDao().insertMovies(result)

    suspend fun deleteMovie(result: com.example.movieapp.models.Result) = db.getMoviesDao().deleteMovie(result)

   fun getLocalMovie() = db.getMoviesDao().getAllMovies()






}