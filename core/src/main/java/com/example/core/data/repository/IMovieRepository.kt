package com.example.core.data.repository

import com.example.core.domain.model.Movie
import com.example.core.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovies(): Flow<ArrayList<Movie>>
    fun searchMovie(query : String) : Flow<ArrayList<Movie>>
    fun getMovieDetail(id : Int) : Flow<MovieDetail>
    fun getFavoriteMovies() : Flow<ArrayList<Movie>>
    suspend fun getFavoriteCount(id: Int) : Int
    suspend fun addFavorite(movie: Movie)
    suspend fun deleteFavorite(movie: Movie)
}