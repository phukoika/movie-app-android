package com.example.movieapp.models

data class SeriesResponse(
    val page: Int,
    val results: List<Series>,
    val total_pages: Int,
    val total_results: Int
)