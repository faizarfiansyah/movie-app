package com.example.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.model.Movie
import com.example.core.domain.usecase.MovieUseCase

class FavoriteViewModel(private val useCase: MovieUseCase): ViewModel() {

    fun getFavoriteMovies() : LiveData<ArrayList<Movie>> = useCase.getFavoriteMovies().asLiveData()
}