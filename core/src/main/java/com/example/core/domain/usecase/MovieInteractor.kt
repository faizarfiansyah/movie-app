package com.example.core.domain.usecase

import com.example.core.data.repository.IMovieRepository
import com.example.core.domain.model.Movie
import com.example.core.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val repository: IMovieRepository): MovieUseCase {
    override fun getMovies(): Flow<ArrayList<Movie>> = repository.getMovies()
    override fun searchMovie(query: String): Flow<ArrayList<Movie>> = repository.searchMovie(query)

    override fun getMovieDetail(id: Int): Flow<MovieDetail> = repository.getMovieDetail(id)

    override fun getFavoriteMovies(): Flow<ArrayList<Movie>> = repository.getFavoriteMovies()

    override suspend fun getFavoriteCount(id: Int): Int = repository.getFavoriteCount(id)

    override suspend fun addFavorite(movie: Movie) = repository.addFavorite(movie)

    override suspend fun deleteFavorite(movie: Movie) = repository.deleteFavorite(movie)
}