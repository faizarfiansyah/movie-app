package com.example.core.data.source.local

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource (private val movieDao: MovieDao) {

    fun getFavoriteMovies() : Flow<List<MovieEntity>> = movieDao.getFavoriteMovies()

    suspend fun getFavoriteCount(id: Int) : Int = movieDao.getFavoriteCount(id)

    suspend fun addFavorite(movie: MovieEntity) = movieDao.addFavorite(movie)

    suspend fun deleteFavorite(movie: MovieEntity) = movieDao.deleteFavorite(movie)

}