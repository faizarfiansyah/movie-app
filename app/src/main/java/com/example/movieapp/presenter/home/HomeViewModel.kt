package com.example.movieapp.presenter.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.model.Movie
import com.example.core.domain.usecase.MovieUseCase

class HomeViewModel(private val useCase: MovieUseCase): ViewModel() {
    fun getMovies() : LiveData<ArrayList<Movie>> = useCase.getMovies().asLiveData()
    fun searchMovie(query : String) : LiveData<ArrayList<Movie>> = useCase.searchMovie(query).asLiveData()
}