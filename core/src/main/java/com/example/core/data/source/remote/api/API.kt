package com.example.core.data.source.remote.api

import com.example.core.data.source.remote.response.MovieDetailResponse
import com.example.core.data.source.remote.response.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey : String = "a99569a50ab7381eb18bac1ae93ff948"
    ): MovieListResponse

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey : String = "a99569a50ab7381eb18bac1ae93ff948",
        @Query("query") query : String
    ) : MovieListResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") id: Int,
        @Query("api_key") apiKey : String = "a99569a50ab7381eb18bac1ae93ff948"
    ): MovieDetailResponse
}