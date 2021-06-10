package com.example.core.utils

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.remote.response.MovieDetailResponse
import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.domain.model.Movie
import com.example.core.domain.model.MovieDetail

object DataMapper {
    fun mapResponseListToDomain(list: ArrayList<MovieResponse>): ArrayList<Movie>{
        val movieList = ArrayList<Movie>()
        list.map {
            val movie = Movie(
                id = it.id,
                title = it.title,
                poster_path = it.poster_path ?: "",
                overview = it.overview
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapResponseObjectToDomain(movie: MovieDetailResponse) : MovieDetail{
        return MovieDetail(
            title = movie.title,
            original_language = movie.original_language,
            overview = movie.overview,
            poster_path = movie.poster_path,
            runtime = movie.runtime,
            vote_average = movie.vote_average,
            vote_count = movie.vote_count,
            budget = movie.budget,
            revenue = movie.revenue,
            release_date = movie.release_date
        )
    }

    fun mapEntityListToDomain(list: List<MovieEntity>): ArrayList<Movie>{
        val movieList = ArrayList<Movie>()
        list.map {
            val movie = Movie(
                id = it.id,
                title = it.title,
                poster_path = it.poster_path,
                overview = it.overview
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapDomainObjectToEntity(movie : Movie) : MovieEntity{
        return MovieEntity(
            id = movie.id,
            title = movie.title,
            poster_path = movie.poster_path,
            overview = movie.overview
        )
    }

    fun mapDetailToMovie(movieDetail: MovieDetail, id: Int) : Movie{
        return Movie(
            id,
            movieDetail.title,
            movieDetail.poster_path ?: "",
            movieDetail.overview
        )
    }

}