package com.example.core.data.source.local.room

import androidx.room.*
import com.example.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(movie: MovieEntity)

    @Query("SELECT * from favorite_movie")
    fun getFavoriteMovies() : Flow<List<MovieEntity>>

    @Query("SELECT count(*) from favorite_movie WHERE id = :id")
    suspend fun getFavoriteCount(id :Int) : Int

    @Delete
    suspend fun deleteFavorite(movie: MovieEntity)
}