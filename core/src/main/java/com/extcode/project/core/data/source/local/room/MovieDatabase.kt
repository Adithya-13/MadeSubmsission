package com.extcode.project.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.extcode.project.core.data.source.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}