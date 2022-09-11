package com.ibrajix.feature.network

import com.ibrajix.feature.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    //get advanced search
    @GET(EndPoints.POPULAR_MOVIES)
    suspend fun getPopularMovies(
        @Query("api_key", encoded = true) apiKey: String = EndPoints.API_KEY,
        @Query("page") page: Int = 1,
    ) : MovieResponse

}