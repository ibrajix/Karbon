package com.ibrajix.feature.repository

import androidx.room.withTransaction
import com.ibrajix.feature.data.database.MovieDatabase
import com.ibrajix.feature.data.model.MovieList
import com.ibrajix.feature.network.MovieApi
import com.ibrajix.feature.utils.Resource
import com.ibrajix.feature.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieRepository  @Inject constructor(
   private val movieApi: MovieApi,
   private val movieDatabase: MovieDatabase
)  {

    private val moviesDao = movieDatabase.movieDao()

    fun getPopularNews(
        forceRefresh: Boolean,
        onFetchSuccess: () -> Unit,
        onFetchFailed: (Throwable) -> Unit
    ): Flow<Resource<List<MovieList>>> =
        networkBoundResource(
            query = {
                moviesDao.getPopularMovies()
            },
            fetch = {
                val response = movieApi.getPopularMovies()
                response.results
            },
            saveFetchResult = { apiMovies->
                movieDatabase.withTransaction {
                    moviesDao.deleteAllMovies()
                    moviesDao.savePopularMovies(apiMovies)
                }
            },
            shouldFetch = { cachedMovies->
                if (forceRefresh){
                    true
                } else {
                   val sortedMovies = cachedMovies.sortedBy {
                       it.id
                   }
                    val oldestTimestamp = sortedMovies.firstOrNull()?.id
                    val needsRefresh = oldestTimestamp == null ||
                            oldestTimestamp < System.currentTimeMillis() -
                            TimeUnit.MINUTES.toMillis(60)
                    needsRefresh
                }
            },
            onFetchSuccess = onFetchSuccess,
            onFetchFailed = { t->
                if (t !is HttpException && t !is IOException){
                    throw t
                }
                onFetchFailed(t)
            }
        )

}