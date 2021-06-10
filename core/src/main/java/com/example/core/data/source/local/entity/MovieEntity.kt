package com.example.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val poster_path: String,
    val overview: String
)