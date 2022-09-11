package com.ibrajix.feature.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.ibrajix.feature.data.dao.MovieDao
import com.ibrajix.feature.data.model.MovieList
import junit.framework.TestCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDatabaseTest : TestCase(){

    private lateinit var db: MovieDatabase
    private lateinit var dao: MovieDao

    @Before
    public override fun setUp() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MovieDatabase::class.java
        ).build()
        dao = db.movieDao()

    }

    @Test
    fun writeAndReadData() = runBlocking{

        val movies = listOf(

            MovieList(
                backdrop_path = "backdrop path",
                id = 1,
                original_title = "original title",
                overview = "overview",
                poster_path = "poster path",
                release_date = "Release Date",
                title = "title",
                vote_average = 2.0),

            MovieList(
                backdrop_path = "backdrop path 2",
                id = 2,
                original_title = "original title 2",
                overview = "overview 2",
                poster_path = "poster path 2",
                release_date = "Release Date 2",
                title = "title 2",
                vote_average = 3.0),


        )

        val moviesAsync = async {
            dao.getPopularMovies().take(2).toList()
        }

        dao.savePopularMovies(movies)

        dao.getPopularMovies()

        val outPutMovies = moviesAsync.await()

        assertThat(outPutMovies.contains(movies)).isTrue()

    }

    @After
    fun closeDb(){
        db.close()
    }

}