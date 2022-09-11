package com.ibrajix.feature.data.model

data class MovieResponse(
    val page: Int,
    val results: List<MovieList>,
    val total_pages: Int,
    val total_results: Int
)