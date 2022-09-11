package com.ibrajix.feature.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ibrajix.feature.data.dao.MovieDao
import com.ibrajix.feature.data.model.MovieList

private const val DATABASE_VERSION = 1

@Database(
    entities = [MovieList::class],
    version = DATABASE_VERSION
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}