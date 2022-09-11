package com.ibrajix.feature.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ibrajix.feature.data.model.MovieList
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieList")
    fun getPopularMovies(): Flow<List<MovieList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePopularMovies(movies: List<MovieList>)

    @Query("DELETE FROM MovieList")
    suspend fun deleteAllMovies()

}