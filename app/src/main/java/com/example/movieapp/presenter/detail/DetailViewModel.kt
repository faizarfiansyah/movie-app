package com.example.movieapp.presenter.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.Movie
import com.example.core.domain.model.MovieDetail
import com.example.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.launch

class DetailViewModel (private val useCase: MovieUseCase): ViewModel() {
    fun getMovieDetail(id : Int) : LiveData<MovieDetail> = useCase.getMovieDetail(id).asLiveData()

    suspend fun getFavoriteCount(id : Int) : Int = useCase.getFavoriteCount(id)

    fun addFavorite(movie: Movie){
        viewModelScope.launch {
            useCase.addFavorite(movie)
        }
    }

    fun removeFavorite(movie: Movie) {
        viewModelScope.launch {
            useCase.deleteFavorite(movie)
        }
    }


}