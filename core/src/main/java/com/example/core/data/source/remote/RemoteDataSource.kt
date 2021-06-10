package com.example.core.data.source.remote

import android.util.Log
import com.example.core.data.source.remote.api.API
import com.example.core.data.source.remote.response.MovieDetailResponse
import com.example.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource (private val api: API) {

    fun getMovies(): Flow<ArrayList<MovieResponse>>{
        return flow{
            try {
                val response = api.getMovies()
                emit(response.results)

            }catch (e: Exception){
                Log.e("RemoteDataSource - Get Movies", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun searchMovie(query : String) : Flow<ArrayList<MovieResponse>>{
        return flow{
            try {
                val response = api.searchMovie(query = query)
                emit(response.results)
            }catch (e: Exception){
                Log.e("RemoteDataSource - Search Movie", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getMovieDetail(id : Int): Flow<MovieDetailResponse>{
        return flow {
            try {
                val response = api.getMovieDetail(id)
                emit(response)
            }catch (e : Exception){
                Log.e("RemoteDataSource - Get Detail", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}