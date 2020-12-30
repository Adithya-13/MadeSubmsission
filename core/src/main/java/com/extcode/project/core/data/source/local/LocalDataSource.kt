package com.extcode.project.core.data.source.local

import com.extcode.project.core.data.source.local.entity.MovieEntity
import com.extcode.project.core.data.source.local.room.MovieDao
import com.extcode.project.core.utils.SortUtils
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val mMovieDao: MovieDao) {

    fun getAllMovies(sort: String): Flow<List<MovieEntity>> {
        val query = SortUtils.getSortedQueryMovies(sort)
        return mMovieDao.getMovies(query)
    }

    fun getAllTvShows(sort: String): Flow<List<MovieEntity>> {
        val query = SortUtils.getSortedQueryTvShows(sort)
        return mMovieDao.getTvShows(query)
    }

    fun getAllFavoriteMovies(sort: String): Flow<List<MovieEntity>> {
        val query = SortUtils.getSortedQueryFavoriteMovies(sort)
        return mMovieDao.getFavoriteMovies(query)
    }

    fun getAllFavoriteTvShows(sort: String): Flow<List<MovieEntity>> {
        val query = SortUtils.getSortedQueryFavoriteTvShows(sort)
        return mMovieDao.getFavoriteTvShows(query)
    }

    suspend fun insertMovies(movies: List<MovieEntity>) = mMovieDao.insertMovie(movies)

    fun setMovieFavorite(movie: MovieEntity, newState: Boolean) {
        movie.favorite = newState
        mMovieDao.updateFavoriteMovie(movie)
    }
}