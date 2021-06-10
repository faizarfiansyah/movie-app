package com.example.core.domain.model

data class MovieDetail(
    val title: String,
    val original_language: String,
    val overview: String,
    val poster_path: String?,
    val runtime: Int,
    val vote_average: Float,
    val vote_count: Int,
    val budget: Int,
    val revenue: Int,
    val release_date: String
)