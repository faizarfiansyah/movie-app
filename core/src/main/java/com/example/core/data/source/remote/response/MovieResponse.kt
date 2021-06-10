package com.example.core.data.source.remote.response

data class MovieResponse(
    val id: Int,
    val title: String,
    val poster_path: String?,
    val overview: String
)