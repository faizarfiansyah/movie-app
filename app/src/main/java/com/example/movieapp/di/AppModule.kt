package com.example.movieapp.di

import com.example.core.domain.usecase.MovieInteractor
import com.example.core.domain.usecase.MovieUseCase
import com.example.movieapp.presenter.detail.DetailViewModel
import com.example.movieapp.presenter.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}
