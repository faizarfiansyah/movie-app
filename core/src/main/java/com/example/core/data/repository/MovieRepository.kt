package com.example.core.data.repository

import android.util.Log
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.domain.model.Movie
import com.example.core.domain.model.MovieDetail
import com.example.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): IMovieRepository {

    override fun getMovies(): Flow<ArrayList<Movie>> {
        return remoteDataSource.getMovies().map {
            DataMapper.mapResponseListToDomain(it) }
    }

    override fun searchMovie(query: String): Flow<ArrayList<Movie>> {
        return remoteDataSource.searchMovie(query).map {
            Log.d("TEST REPO", query)
            DataMapper.mapResponseListToDomain(it) }
    }

    override fun getMovieDetail(id :Int): Flow<MovieDetail> {
        return remoteDataSource.getMovieDetail(id).map {
            DataMapper.mapResponseObjectToDomain(it)
        }
    }

    override fun getFavoriteMovies(): Flow<ArrayList<Movie>> {
        return localDataSource.getFavoriteMovies().map {
            DataMapper.mapEntityListToDomain(it)
        }
    }

    override suspend fun getFavoriteCount(id: Int): Int {
        return localDataSource.getFavoriteCount(id)
    }

    override suspend fun addFavorite(movie: Movie) {
        val entity = DataMapper.mapDomainObjectToEntity(movie)
        localDataSource.addFavorite(entity)
    }

    override suspend fun deleteFavorite(movie: Movie) {
        val entity = DataMapper.mapDomainObjectToEntity(movie)
        localDataSource.deleteFavorite(entity)
    }


}