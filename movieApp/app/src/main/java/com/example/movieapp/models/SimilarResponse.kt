package com.example.movieapp.models

data class SimilarResponse(
    val page: Int,
    val results: List<Similar>,
    val total_pages: Int,
    val total_results: Int
)