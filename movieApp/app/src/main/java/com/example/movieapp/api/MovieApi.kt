package com.example.movieapp.api

import com.example.movieapp.models.*
import com.example.movieapp.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("page")
        pageNumber: Int = 1,
        @Query("api_key")
        api_key : String = API_KEY
    ) : Response<NewResponse>

    @GET("/3/search/movie")
    suspend fun getSearchMovie(
        @Query("page")
        pageNumber: Int = 1,
        @Query("query")
        query : String = "",
        @Query("api_key")
        api_key : String = API_KEY,

    ) : Response<NewResponse>

    @GET("3/movie/top_rated")
    suspend fun getTopRated(
        @Query("page")
        pageNumber: Int = 1,
        @Query("api_key")
        api_key : String = API_KEY
    ) : Response<NewResponse>

    @GET("3/movie/{movie_id}/credits")
    suspend fun getActors(
        @Path("movie_id")
        movie : String = "1",
        @Query("api_key")
        api_key : String = API_KEY
    ) : Response<CastResponse>

    @GET("3/movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id")
        movie : String = "1",
        @Query("api_key")
        api_key : String = API_KEY
    ) : Response<ReviewsResponse>

    @GET("3/movie/{movie_id}/recommendations")
    suspend fun getSimilarMovie(
        @Path("movie_id")
        movie : String = "1",
        @Query("api_key")
        api_key : String = API_KEY
    ) : Response<SimilarResponse>

    @GET("3/tv/popular")
    suspend fun getSeries(
        @Query("page")
        pageNumber: Int = 1,
        @Query("api_key")
        api_key : String = API_KEY
    ) : Response<SeriesResponse>

    @GET("3/movie/{movie_id}/videos")
    suspend fun getVideo(
        @Path("movie_id")
        movie : String = "1",
        @Query("api_key")
        api_key : String = API_KEY
    ) : Response<VideoResponse>

}